package com.pwhs.quickmem.domain.model.auth

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponseModel(
    val email: String,
    val isReset: Boolean,
    val message: String
)