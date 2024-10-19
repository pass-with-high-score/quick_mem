package com.pwhs.quickmem.data.dto.verify_email

import com.google.gson.annotations.SerializedName

data class EmailRequestDto(
    @SerializedName("to_email")
    val toEmail: String
)
