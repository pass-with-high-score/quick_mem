package com.pwhs.quickmem.presentation.app.deeplink.classes

import kotlinx.serialization.Serializable

@Serializable
data class JoinClassArgs(
    val code: String,
    val isFromDeepLink: Boolean
)