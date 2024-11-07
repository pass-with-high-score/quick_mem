package com.pwhs.quickmem.domain.model.users

data class ClassMemberModel(
    val id: String,
    val username: String,
    val avatarUrl: String,
    val isOwner: Boolean,
    val role: String,
)