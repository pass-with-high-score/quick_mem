package com.pwhs.quickmem.data.dto.classes

data class RemoveMembersRequestDto(
    val userId: String,
    val classId: String,
    val memberIds: List<String>
)