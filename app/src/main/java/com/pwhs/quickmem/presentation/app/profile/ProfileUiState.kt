package com.pwhs.quickmem.presentation.app.profile

import com.pwhs.quickmem.domain.model.streak.StreakModel
import com.pwhs.quickmem.domain.model.study_time.GetStudyTimeByUserResponseModel
import com.revenuecat.purchases.CustomerInfo
import java.time.LocalDate

data class ProfileUiState(
    val userAvatar: String = "",
    val username: String = "",
    val role : String = "",
    val customerInfo: CustomerInfo? = null,
    val isLoading: Boolean = false,
    val streakCount: Int = 0,
    val streaks: List<StreakModel> = emptyList(),
    val streakDates: List<LocalDate> = emptyList(),
    val studyTime: GetStudyTimeByUserResponseModel? = null,

)