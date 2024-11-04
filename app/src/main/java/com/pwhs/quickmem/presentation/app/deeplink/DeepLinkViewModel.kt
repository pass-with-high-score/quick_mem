package com.pwhs.quickmem.presentation.app.deeplink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.core.datastore.AppManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class DeepLinkViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(DeepLinkUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<DeepLinkUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

//    init {
//        viewModelScope.launch {
//            appManager.isLoggedIn.collectLatest { isLoggedIn ->
//                if (!isLoggedIn) {
//                    _uiEvent.trySend(DeepLinkUiEvent.UnAuthorized)
//                }
//            }
//        }
//    }

    fun onEvent(event: DeepLinkUiAction) {
        Timber.d("DeepLinkViewModel: onEvent run on uievent: $event")
        when (event) {
            is DeepLinkUiAction.TriggerEvent -> {
                Timber.d("DeepLinkViewModel: onEvent run in when: $event")
                when {
                    event.studySetCode != null -> {
                        _uiEvent.trySend(DeepLinkUiEvent.ShareStudySet(event.studySetCode))
                    }

                    event.folderCode != null -> {
                        _uiEvent.trySend(DeepLinkUiEvent.ShareFolder(event.folderCode))
                    }

                    event.classCode != null -> {
                        Timber.d("DeepLinkViewModel: onEvent send to class: $event")
                        _uiEvent.trySend(DeepLinkUiEvent.JoinClass(event.classCode))
                    }
                }
            }
        }
    }
}