package com.pwhs.quickmem.core.data

import androidx.compose.ui.focus.FocusRequester

data class OtpValue(
    val value: String = "",
    var focused: Boolean = false,
    val focusRequester: FocusRequester? = null,
)
