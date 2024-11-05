package com.pwhs.quickmem.presentation.app.settings.user_info.email

import com.pwhs.quickmem.data.dto.auth.UpdateEmailRequestDto

data class UpdateEmailRequestModel(
    val userId: String,
    val email: String
) {
    fun toDto(): UpdateEmailRequestDto {
        return UpdateEmailRequestDto(
            userId = this.userId,
            email = this.email
        )
    }
}
