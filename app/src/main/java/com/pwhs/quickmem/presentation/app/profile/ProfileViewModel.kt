package com.pwhs.quickmem.presentation.app.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.streak.StreakModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.domain.repository.StreakRepository
import com.pwhs.quickmem.domain.repository.StudyTimeRepository
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
import java.time.LocalDate
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appManager: AppManager,
    private val tokenManager: TokenManager,
    private val authRepository: AuthRepository,
    private val studyTimeRepository: StudyTimeRepository,
    private val streakRepository: StreakRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var job: Job? = null

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            if (token.isNotEmpty() && userId.isNotEmpty()) {
                loadProfile()
                getUserProfile(token = token, userId = userId)
                getCustomerInfo()
                getStudyTime(token = token, userId = userId)
                getStreaksByUserId(token = token, userId = userId)
            }
        }
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
                job?.cancel()
                job = viewModelScope.launch {
                    delay(500)
                    initData()
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
                Timber.e(error.message)
            }
        })
    }

    private fun getUserProfile(token: String, userId: String) {
        viewModelScope.launch {

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
                            appManager.saveUserName(data.username)
                            appManager.saveUserAvatar(data.avatarUrl)
                            appManager.saveUserRole(data.role)
                            appManager.saveUserFullName(data.fullname)
                            appManager.saveUserEmail(data.email)
                            appManager.saveUserId(data.id)
                            appManager.saveUserCoins(data.coin)
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
                appManager.username.combine(appManager.userAvatarUrl) { username, avatar ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            username = username,
                            userAvatar = avatar,
                        )
                    }
                }.collect()
                appManager.userRole.collect { role ->
                    _uiState.update {
                        it.copy(
                            role = role
                        )
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error observing DataStore")
            }
        }
    }

    private fun getStudyTime(token: String, userId: String) {
        viewModelScope.launch {

            studyTimeRepository.getStudyTimeByUser(token, userId).collectLatest { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        resource.data?.let { data ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    studyTime = data
                                )
                            }
                        }
                    }

                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        Timber.e("Error fetching study time: ${resource.message}")
                    }
                }
            }
        }
    }

    private fun getStreaksByUserId(token: String, userId: String) {
        viewModelScope.launch {
            streakRepository.getStreaksByUserId(token, userId).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    is Resources.Success -> {
                        val streaks = resource.data?.streaks ?: emptyList()
                        val streakDates = calculateStreakDates(streaks)

                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            streaks = streaks,
                            streakDates = streakDates
                        )
                        Timber.d("Dates: $streakDates")
                    }

                    is Resources.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                }
            }
        }
    }

    private fun calculateStreakDates(streaks: List<StreakModel>): List<LocalDate> {
        return streaks.flatMap { streak ->
            val firstStreakDate = OffsetDateTime.parse(streak.date).toLocalDate()
            (0 until streak.streakCount).map {
                firstStreakDate.minusDays(it.toLong())
            }
        }.distinct()
    }
}