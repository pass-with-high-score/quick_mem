package com.pwhs.quickmem.presentation.app.deeplink

sealed class DeepLinkUiAction {
    data class TriggerEvent(
        val studySetCode: String? = null,
        val folderCode: String? = null,
        val classCode: String? = null
    ) : DeepLinkUiAction()
}