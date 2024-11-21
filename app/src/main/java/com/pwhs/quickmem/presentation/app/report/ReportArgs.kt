package com.pwhs.quickmem.presentation.app.report

data class ReportArgs(
    val reportType: ReportTypeEnum,
    val userID: String? = null,
    val userName: String? = null,
    val studySetID: String? = null,
    val classID: String? = null,
    val ownerName: String? = null
)

