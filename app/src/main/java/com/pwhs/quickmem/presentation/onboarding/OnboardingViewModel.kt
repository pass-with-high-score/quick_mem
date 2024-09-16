package com.pwhs.quickmem.presentation.onboarding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.pwhs.quickmem.core.utils.SharedPrefUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val application: Application
) : AndroidViewModel(application) {
    fun checkAuth(){
       val isLoggedIn = SharedPrefUtils.getBoolean(application.applicationContext, "is_logged_in")
        if (isLoggedIn) {
            // Navigate to Home
        } else {
            // Navigate to Auth
        }
    }
}