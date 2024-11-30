package com.pwhs.quickmem.presentation.app.explore

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.data.enums.CoinAction
import com.pwhs.quickmem.core.data.enums.DifficultyLevel
import com.pwhs.quickmem.core.data.enums.QuestionType
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.study_set.CreateStudySetByAIRequestModel
import com.pwhs.quickmem.domain.model.users.UpdateCoinRequestModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import com.pwhs.quickmem.domain.repository.StreakRepository
import com.pwhs.quickmem.domain.repository.StudySetRepository
import com.pwhs.quickmem.util.getLanguageCode
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
    private val streakRepository: StreakRepository,
    private val studySetRepository: StudySetRepository,
    private val authRepository: AuthRepository,
    application: Application
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ExploreUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val languageCode = getApplication<Application>().getLanguageCode()
        viewModelScope.launch {
            combine(appManager.userId, appManager.userCoins) { userId, coins ->
                _uiState.update {
                    it.copy(
                        ownerId = userId,
                        coins = coins,
                        language = languageCode
                    )
                }
            }.collectLatest {
                getTopStreaks()
                getCustomerInfo()
            }
        }
    }

    fun onEvent(event: ExploreUiAction) {
        when (event) {
            ExploreUiAction.RefreshTopStreaks -> {
                getTopStreaks()
            }

            is ExploreUiAction.OnDescriptionChanged -> {
                _uiState.update { it.copy(description = event.description) }
            }

            is ExploreUiAction.OnDifficultyLevelChanged -> {
                _uiState.update { it.copy(difficulty = event.difficultyLevel) }
            }

            is ExploreUiAction.OnLanguageChanged -> {
                _uiState.update { it.copy(language = event.language) }
            }

            is ExploreUiAction.OnNumberOfFlashcardsChange -> {
                _uiState.update {
                    it.copy(
                        numberOfFlashcards = event.numberOfCards
                    )
                }
            }

            is ExploreUiAction.OnQuestionTypeChanged -> {
                _uiState.update { it.copy(questionType = event.questionType) }
            }

            is ExploreUiAction.OnTitleChanged -> {
                _uiState.update { it.copy(title = event.title) }
            }

            is ExploreUiAction.OnCreateStudySet -> {
                if (uiState.value.title.isEmpty()) {
                    _uiState.update {
                        it.copy(errorMessage = "Title is required")
                    }
                } else {
                    createStudySet()
                }
            }

            is ExploreUiAction.OnEarnCoins -> {
                updateCoins(coinAction = CoinAction.ADD, coin = 1)
            }
        }
    }

    private fun getTopStreaks() {
        viewModelScope.launch {
            tokenManager.accessToken.collect { token ->
                streakRepository.getTopStreaks(token = token ?: "", limit = 10)
                    .collect { resource ->
                        when (resource) {
                            is Resources.Loading -> {
                                _uiState.update { it.copy(isLoading = true) }
                            }

                            is Resources.Success -> {
                                val topStreaks = resource.data ?: emptyList()
                                val streakOwner =
                                    topStreaks.find { it.userId == uiState.value.ownerId }
                                val rankOwner =
                                    topStreaks.indexOfFirst { it.userId == uiState.value.ownerId }
                                        .takeIf { it != -1 }?.plus(1)
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        topStreaks = topStreaks,
                                        streakOwner = streakOwner,
                                        rankOwner = rankOwner
                                    )
                                }
                            }

                            is Resources.Error -> {
                                _uiState.update { it.copy(isLoading = false) }
                                _uiEvent.send(ExploreUiEvent.Error(resource.message ?: ""))
                            }
                        }
                    }
            }

        }
    }

    private fun createStudySet() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            val createStudySetByAIRequestModel = CreateStudySetByAIRequestModel(
                title = uiState.value.title,
                description = uiState.value.description,
                difficulty = uiState.value.difficulty.level,
                language = uiState.value.language,
                numberOfFlashcards = uiState.value.numberOfFlashcards,
                questionType = uiState.value.questionType.type,
                userId = userId
            )
            studySetRepository.createStudySetByAI(
                token = token,
                createStudySetByAIRequestModel
            ).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "",
                                title = "",
                                description = "",
                                numberOfFlashcards = 10,
                                questionType = QuestionType.MULTIPLE_CHOICE,
                                difficulty = DifficultyLevel.EASY,
                                language = getApplication<Application>().getLanguageCode()
                            )
                        }
                        if (_uiState.value.customerInfo?.activeSubscriptions?.isNotEmpty() == false) {
                            updateCoins(coinAction = CoinAction.SUBTRACT, coin = 1)
                        }
                        _uiEvent.send(
                            ExploreUiEvent.CreatedStudySet(
                                studySetId = resource.data?.id ?: ""
                            )
                        )
                    }

                    is Resources.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = resource.message ?: ""
                            )
                        }
                        _uiEvent.send(ExploreUiEvent.Error(resource.message ?: ""))
                    }
                }
            }
        }
    }

    private fun updateCoins(
        coinAction: CoinAction,
        coin: Int = 1
    ) {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            authRepository.updateCoin(
                token, UpdateCoinRequestModel(
                    userId = userId,
                    action = coinAction.action,
                    coin = coin
                )
            ).collect { coin ->
                when (coin) {
                    is Resources.Error -> {
                        Timber.e("Too many requests, please wait 1 minute")
                        _uiEvent.send(ExploreUiEvent.Error("Too many requests, please wait 1 minute"))
                    }

                    is Resources.Loading -> {
                        // do nothing
                    }

                    is Resources.Success -> {
                        appManager.saveUserCoins(coin.data?.coins ?: 0)
                        _uiState.update {
                            it.copy(coins = coin.data?.coins ?: 0)
                        }
                        Toast.makeText(
                            getApplication(),
                            "You have earned 1 coin",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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
}