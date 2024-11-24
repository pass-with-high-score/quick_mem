package com.pwhs.quickmem.presentation.app.settings.user_info.change_role

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import com.pwhs.quickmem.core.datastore.TokenManager
import com.pwhs.quickmem.core.utils.Resources
import com.pwhs.quickmem.domain.model.auth.ChangeRoleRequestModel
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ChangeRoleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tokenManager: TokenManager,
    private val authRepository: AuthRepository,
    private val appManager: AppManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChangeRoleUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ChangeRoleUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val userId = savedStateHandle.get<String>("userId") ?: ""
        val role = savedStateHandle.get<String>("role") ?: ""
        _uiState.update {
            it.copy(
                userId = userId,
                role = role
            )
        }
    }

    fun onEvent(event: ChangeRoleUiAction) {
        when (event) {
            is ChangeRoleUiAction.SelectRole -> {
                _uiState.update { it.copy(role = event.role, errorMessage = null) }
            }

            is ChangeRoleUiAction.SaveRole -> {
                changeRole()
            }
        }
    }

    private fun changeRole() {
        viewModelScope.launch {
            val birthday = appManager.userBirthday.firstOrNull() ?: ""
            if (!isUserOver20(birthday)) {
                val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val eligibleDate = calculateEligibleDate(birthday)
                val eligibleDateString = dateFormatter.format(eligibleDate)
                _uiEvent.send(
                    ChangeRoleUiEvent.ShowUnderageDialog(
                        eligibleDateString
                    )
                )
                return@launch
            }
            val token = tokenManager.accessToken.firstOrNull() ?: ""
            val userId = _uiState.value.userId
            val role = _uiState.value.role
            authRepository.changeRole(
                token = token,
                changeRoleRequestModel = ChangeRoleRequestModel(
                    userId = userId,
                    role = role
                )
            ).collect { resource ->
                when (resource) {
                    is Resources.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resources.Error -> {
                        _uiState.update { it.copy(isLoading = false, errorMessage = resource.message) }
                        _uiEvent.send(ChangeRoleUiEvent.ShowError(resource.message ?: "An error occurred"))
                    }

                    is Resources.Success -> {
                        appManager.saveUserRole(role)
                        _uiState.update { it.copy(isLoading = false) }
                        _uiEvent.send(ChangeRoleUiEvent.RoleChangedSuccessfully)
                    }
                }
            }
        }
    }

    private fun isUserOver20(birthday: String): Boolean {
        if (birthday.isEmpty()) return false
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val birthdayDate = dateFormatter.parse(birthday) ?: return false
        val calendar = Calendar.getInstance()
        calendar.time = birthdayDate
        calendar.add(Calendar.YEAR, 20)
        return calendar.time.before(Date())
    }

    private fun calculateEligibleDate(birthday: String): Date {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val birthdayDate = dateFormatter.parse(birthday) ?: return Date()
        val calendar = Calendar.getInstance()
        calendar.time = birthdayDate
        calendar.add(Calendar.YEAR, 20)
        return calendar.time
    }
}
