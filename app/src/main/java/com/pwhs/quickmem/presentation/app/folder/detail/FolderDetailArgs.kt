package com.pwhs.quickmem.presentation.app.folder.detail

import kotlinx.serialization.Serializable

@Serializable
data class FolderDetailArgs (
    val id: String? = null,
    val code: String? = null,
)