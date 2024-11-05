package com.pwhs.quickmem.data.mapper.classes

data class AddMemberToClassRequestModel(
    val joinToken: String,
    val userId: String,
    val classId: String
)