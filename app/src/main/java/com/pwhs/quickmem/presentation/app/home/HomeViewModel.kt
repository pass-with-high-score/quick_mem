package com.pwhs.quickmem.presentation.app.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.streak.StreakModel
import com.pwhs.quickmem.domain.model.subject.GetTop5SubjectResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.domain.repository.ClassRepository
import com.pwhs.quickmem.domain.repository.FolderRepository
import com.pwhs.quickmem.domain.repository.NotificationRepository
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
import timber.log.Timber
import java.time.LocalDate
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val streakRepository: StreakRepository,
    private val studySetRepository: StudySetRepository,
    private val folderRepository: FolderRepository,
    private val classRepository: ClassRepository,
    private val authRepository: AuthRepository,
    private val notificationRepository: NotificationRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        viewModelScope.launch {
            val userId = appManager.userId.firstOrNull() ?: ""
            _uiState.value = HomeUiState(userId = userId)
            updateStreak()
            getCustomerInfo()
            loadNotifications()
            getTop5Subjects()
        }
    }

    init {
        updateStreak()
        getCustomerInfo()
        getStreaksByUserId()
    }


    fun onEvent(event: HomeUiAction) {
        when (event) {
            is HomeUiAction.OnChangeAppPushNotifications -> {
                viewModelScope.launch {
                    appManager.saveAppPushNotifications(event.isAppPushNotificationsEnabled)
                }
            }

            is HomeUiAction.OnChangeCustomerInfo -> {
                _uiState.update {
                    it.copy(
                        customerInfo = event.customerInfo
                    )
                }
            }

            is HomeUiAction.LoadNotifications -> {
                loadNotifications()
            }

            is HomeUiAction.MarkAsRead -> {
                markNotificationAsRead(event.notificationId)
            }

            is HomeUiAction.RefreshNotifications -> {
                loadNotifications()
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

    private fun getStreaksByUserId() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
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
                        Timber.d("Streak count: ${resource.data?.streakCount}")
                    }

                    is Resources.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                }
            }
        }
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            notificationRepository.loadNotifications(userId, token).collect { result ->
                when (result) {
                    is Resources.Loading -> {
                        // do nothing
                    }

                    is Resources.Success -> _uiState.update { state ->
                        val notificationCount = result.data?.count { !it.isRead } ?: 0
                        state.copy(
                            isLoading = false,
                            notifications = result.data ?: emptyList(),
                            notificationCount = notificationCount,
                            error = null
                        )
                    }

                    is Resources.Error -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Failed to load notifications"
                        )
                    }
                }
            }
        }
    }

    private fun markNotificationAsRead(notificationId: String) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            notificationRepository.markNotificationAsRead(notificationId, token).collect { result ->
                when (result) {
                    is Resources.Success -> _uiState.update { state ->
                        state.copy(
                            notifications = state.notifications.map { notification ->
                                if (notification.id == notificationId) notification.copy(isRead = true) else notification
                            },
                            notificationCount = state.notificationCount - 1,
                        )
                    }

                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(error = result.message ?: "Failed to mark notification as read")
                        }
                    }

                    is Resources.Loading -> {
                        // do nothing
                    }
                }
            }
        }
    }

    private fun getTop5Subjects() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            studySetRepository.getTop5Subject(token).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    is Resources.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            top5Subjects = getTopSubjects(resource.data ?: emptyList())
                        )
                        Timber.d("SubjectModels: ${getTopSubjects(resource.data ?: emptyList())}")
                        Timber.d("Top 5 subjects: ${resource.data}")
                    }

                    is Resources.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                }
            }
        }
    }

    fun getTopSubjects(
        top5Subjects: List<GetTop5SubjectResponseModel>,
        subjectModels: List<SubjectModel> = SubjectModel.defaultSubjects
    ): List<SubjectModel> {
        val topSubjectIds = top5Subjects.map { it.id }
        return subjectModels.filter { it.id in topSubjectIds }
    }
}