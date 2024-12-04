package com.pwhs.quickmem.presentation.app.report

data class ReportArgs(
    val reportType: ReportTypeEnum,
    val reportedEntityId: String,
    val ownerOfReportedEntity: String,
)

