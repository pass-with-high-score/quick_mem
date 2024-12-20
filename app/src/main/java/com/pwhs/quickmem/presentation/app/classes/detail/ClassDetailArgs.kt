package com.pwhs.quickmem.presentation.app.classes.detail

import kotlinx.serialization.Serializable

@Serializable
data class ClassDetailArgs(
    val title: String,
    val description: String,
    val id: String,
)