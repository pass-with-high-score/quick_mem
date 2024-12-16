package com.pwhs.quickmem.data.dto.classes

import com.google.gson.annotations.SerializedName
import com.pwhs.quickmem.core.data.enums.InviteStatus

data class InviteToClassResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("inviteStatus")
    val inviteStatus: String = InviteStatus.SUCCESS.name
)
