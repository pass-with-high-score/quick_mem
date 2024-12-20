package com.pwhs.quickmem.presentation.app.report

import kotlinx.serialization.Serializable

@Serializable
data class ReportArgs(
    val reportType: ReportTypeEnum,
    val reportedEntityId: String,
    val ownerOfReportedEntity: String,
)

