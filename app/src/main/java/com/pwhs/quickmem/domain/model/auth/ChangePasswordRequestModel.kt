package com.pwhs.quickmem.domain.model.auth


data class ChangePasswordRequestModel(
    val userId: String,
    val currentPassword: String,
    val newPassword: String,
    val confirmPassword: String
)