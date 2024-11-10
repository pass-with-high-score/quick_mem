package com.pwhs.quickmem.presentation.app.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.repository.ClassRepository
import com.pwhs.quickmem.domain.repository.FolderRepository
import com.pwhs.quickmem.domain.repository.StreakRepository
import com.pwhs.quickmem.domain.repository.StudySetRepository
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val streakRepository: StreakRepository,
    private val studySetRepository: StudySetRepository,
    private val folderRepository: FolderRepository,
    private val classRepository: ClassRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        updateStreak()
        getCustomerInfo()
    }

    fun onEvent(event: HomeUIAction) {
        when (event) {
            is HomeUIAction.OnChangeAppPushNotifications -> {
                viewModelScope.launch {
                    appManager.saveAppPushNotifications(event.isAppPushNotificationsEnabled)
                }
            }

            is HomeUIAction.OnChangeCustomerInfo -> {
                _uiState.update {
                    it.copy(
                        customerInfo = event.customerInfo
                    )
                }
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
            }
        })
    }

    private fun updateStreak() {
        viewModelScope.launch {
            val userId = appManager.userId.firstOrNull() ?: ""
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            streakRepository.updateStreak(token, userId).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    is Resources.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            streakCount = resource.data?.streakCount ?: 0
                        )
                    }

                    is Resources.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                }
            }
        }
    }
}