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
        viewModelScope.launch {
            delay(3000)
            appManager.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    _uiEvent.send(SplashUiEvent.IsLoggedIn)
                } else {
                    checkFirstRun()
                }
            }
        }
    }

    private fun checkFirstRun() {
        viewModelScope.launch {
            appManager.isFirstRun.collect { isFirstRun ->
                if (isFirstRun) {
                    appManager.saveIsFirstRun(true)
                    _uiEvent.send(SplashUiEvent.FirstRun)
                } else {
                    _uiEvent.send(SplashUiEvent.NotFirstRun)
                }
            }
        }
    }
}