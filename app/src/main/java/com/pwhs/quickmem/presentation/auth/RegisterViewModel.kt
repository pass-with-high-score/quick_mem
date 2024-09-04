package com.pwhs.quickmem.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.domain.model.UserRole
import com.pwhs.quickmem.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.exceptions.BadRequestRestException
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.UnknownRestException
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    suspend fun register(
        email: String,
        userName: String,
        password: String,
        fullName: String,
        birthDay: Date,
        role: UserRole
    ): Boolean {
        Timber.d("Registering user with email: $email")
        return try {
            authRepository.register(email, userName, password, fullName, birthDay, role)
        } catch (e: BadRequestRestException) {
            Timber.e(e.message)
            false
        } catch (e: UnknownRestException) {
            Timber.e(e.message)
            false
        }catch (e: HttpRequestException) {
            Timber.e(e.cause)
            false
        }
    }
}