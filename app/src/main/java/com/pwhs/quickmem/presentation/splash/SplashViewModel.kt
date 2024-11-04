package com.pwhs.quickmem.presentation.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appManager: AppManager,
    application: Application
) : AndroidViewModel(application) {

    private val _uiEvent = Channel<SplashUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        checkAuth()
    }

    private fun checkAuth() {
        Timber.d("Checking auth")
        viewModelScope.launch {
            delay(3000)
            appManager.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    _uiEvent.send(SplashUiEvent.IsLoggedIn)
                } else {
                    _uiEvent.send(SplashUiEvent.NotLoggedIn)
                    checkFirstRun()
                }
            }
        }
    }

    private fun checkFirstRun() {
        Timber.d("Checking first run")
        viewModelScope.launch {
            delay(3000)
            appManager.isFirstRun.collect { isFirstRun ->
                if (isFirstRun) {
                    _uiEvent.send(SplashUiEvent.FirstRun)
                } else {
                    appManager.saveIsFirstRun(true)
                    _uiEvent.send(SplashUiEvent.NotFirstRun)
                }
            }
        }
    }
}