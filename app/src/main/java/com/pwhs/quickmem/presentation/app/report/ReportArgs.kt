package com.pwhs.quickmem.presentation.app.report

data class ReportArgs(
    val reportType: ReportTypeEnum,
    val username: String? = null,
    val studySetId: String? = null,
    val classId: String? = null,
    val userId: String? = null
)

