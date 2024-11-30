package com.pwhs.quickmem.presentation.app.deeplink.classes

sealed class JoinClassUiEvent {
    data class JoinedClass(
        val classCode: String, val id: String,
        val title: String,
        val description: String,
    ) : JoinClassUiEvent()
    data object UnAuthorized : JoinClassUiEvent()
    data object NotFound : JoinClassUiEvent()
}