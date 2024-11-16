package com.pwhs.quickmem.presentation.app.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appManager: AppManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadProfile()
        getCustomerInfo()
    }

    fun onEvent(event: ProfileUiAction) {
        when (event) {
            is ProfileUiAction.LoadProfile -> loadProfile()
            is ProfileUiAction.OnChangeCustomerInfo -> {
                _uiState.update {
                    it.copy(
                        customerInfo = event.customerInfo
                    )
                }
            }

            ProfileUiAction.Refresh -> {
                loadProfile()
            }
        }
    }

    private fun loadProfile() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        try {
            viewModelScope.launch {
                val username = appManager.userName.firstOrNull() ?: ""
                val avatar = appManager.userAvatar.firstOrNull() ?: ""
                _uiState.value = _uiState.value.copy(
                    username = username,
                    userAvatar = avatar,
                    isLoading = false
                )
            }
        } catch (e: Exception) {
            Timber.e(e)
            _uiState.update {
                it.copy(isLoading = false)
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
                // handle error
                Timber.e(error.message)
            }
        })
    }
}
