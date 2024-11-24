package com.pwhs.quickmem.presentation.app.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appManager: AppManager,
    private val tokenManager: TokenManager,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadProfile()
        getUserProfile()
        getCustomerInfo()
    }

    fun onEvent(event: ProfileUiAction) {
        when (event) {
            is ProfileUiAction.OnChangeCustomerInfo -> {
                _uiState.update {
                    it.copy(
                        customerInfo = event.customerInfo
                    )
                }
            }

            ProfileUiAction.Refresh -> {
                getUserProfile()
                getCustomerInfo()
            }
        }
    }

    private fun getCustomerInfo() {
        Purchases.sharedInstance.getCustomerInfo(object : ReceiveCustomerInfoCallback {
            override fun onReceived(customerInfo: CustomerInfo) {
                _uiState.update {
                    it.copy(
                        customerInfo = customerInfo
                    )
                }
            }

            override fun onError(error: PurchasesError) {
                Timber.e(error.message)
            }
        })
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""

            authRepository.getUserProfile(token, userId).collectLatest { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        resource.data?.let { data ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    username = data.username,
                                    role = data.role,
                                    userAvatar = data.avatarUrl
                                )
                            }
                            appManager.saveUserRole(data.role)
                            appManager.saveUserName(data.username)
                            appManager.saveUserAvatar(data.avatarUrl)
                        }
                    }

                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        Timber.e("Error fetching profile: ${resource.message}")
                    }
                }
            }
        }
    }

    private fun loadProfile() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            try {
                appManager.userName.combine(appManager.userAvatar) { username, avatar ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            username = username,
                            userAvatar = avatar
                        )
                    }
                }.collect()
            } catch (e: Exception) {
                Timber.e(e, "Error observing DataStore")
            }
        }
    }
}