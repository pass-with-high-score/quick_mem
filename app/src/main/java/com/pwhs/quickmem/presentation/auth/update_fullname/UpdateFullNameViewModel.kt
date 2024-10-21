package com.pwhs.quickmem.presentation.auth.update_fullname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.UpdateFullNameRequestModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UpdateFullNameViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager,
    private val appManager: AppManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UpdateFullNameUIState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UpdateFullNameUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: UpdateFullNameUIAction) {
        when (event) {
            is UpdateFullNameUIAction.FullNameChanged -> {
                Timber.d("FullNameChanged: New name = ${event.fullname}")
                if (event.fullname.isBlank()) {
                    _uiState.update {
                        it.copy(
                            fullName = event.fullname,
                            errorMessage = "Your name cannot be empty"
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            fullName = event.fullname,
                            errorMessage = ""
                        )
                    }
                }
            }

            is UpdateFullNameUIAction.Submit -> {
                Timber.d("Submit action triggered. Full name: ${_uiState.value.fullName}")
                updateFullName()
            }
        }
    }

    private fun updateFullName() {
        viewModelScope.launch {
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = appManager.userId.firstOrNull() ?: ""
            Timber.d("Updating full name. Token: $token, UserId: $userId, FullName: ${_uiState.value.fullName}")

            _uiState.update { it.copy(isLoading = true) }

            authRepository.updateFullName(
                token, UpdateFullNameRequestModel(
                    userId = userId,
                    fullname = _uiState.value.fullName
                )
            ).collect { resource ->
                when (resource) {
                    is Resources.Error -> {
                        Timber.e("Error: ${resource.message}")
                        _uiState.update { it.copy(isLoading = false) }
                    }

                    is Resources.Loading -> {
                        Timber.d("Loading")
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Success -> {
                        Timber.d("Update Success: ${resource.data}")
                        _uiState.update {
                            Timber.d("Resetting fullName after successful update.")
                            it.copy(
                                fullName = "",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("ViewModel onCleared called, closing UI Event Channel.")
        _uiEvent.close()
    }
}