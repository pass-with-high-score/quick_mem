package com.pwhs.quickmem.presentation.app.deeplink.folder

import kotlinx.serialization.Serializable

@Serializable
data class LoadFolderArgs(
    val folderCode: String,
)