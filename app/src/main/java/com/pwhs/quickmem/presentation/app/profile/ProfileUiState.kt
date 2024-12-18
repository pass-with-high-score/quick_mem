package com.pwhs.quickmem.presentation.app.profile

import com.pwhs.quickmem.domain.model.study_time.GetStudyTimeByUserResponseModel
import com.revenuecat.purchases.CustomerInfo

data class ProfileUiState(
    val userAvatar: String = "",
    val username: String = "",
    val role: String = "",
    val customerInfo: CustomerInfo? = null,
    val isLoading: Boolean = false,
    val studyTime: GetStudyTimeByUserResponseModel? = null,
)