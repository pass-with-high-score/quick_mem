package com.pwhs.quickmem.presentation.app.deeplink

sealed class DeepLinkUiEvent {
    data object UnAuthorized : DeepLinkUiEvent()
    data class JoinClass(val classCode: String) : DeepLinkUiEvent()
    data class ShareFolder(val folderCode: String) : DeepLinkUiEvent()
    data class ShareStudySet(val studySetCode: String) : DeepLinkUiEvent()
}