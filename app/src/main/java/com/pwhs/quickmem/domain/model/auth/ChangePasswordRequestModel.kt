package com.pwhs.quickmem.domain.model.auth

data class ChangePasswordRequestModel(
    val email: String,
    val oldPassword: String,
    val newPassword: String,
)