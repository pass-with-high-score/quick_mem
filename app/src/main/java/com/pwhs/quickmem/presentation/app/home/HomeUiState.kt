package com.pwhs.quickmem.presentation.app.home

import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.notification.GetNotificationResponseModel
import com.pwhs.quickmem.domain.model.streak.StreakModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.subject.GetTop5SubjectResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.revenuecat.purchases.CustomerInfo
import java.time.LocalDate

data class HomeUiState(
    val isLoading: Boolean = false,
    val userId: String = "",
    val streakCount: Int = 0,
    val notificationCount: Int = 0,
    val customerInfo: CustomerInfo? = null,
    val notifications: List<GetNotificationResponseModel> = emptyList(),
    val error: String? = null,
    val streaks: List<StreakModel> = emptyList(),
    val streakDates: List<LocalDate> = emptyList(),
    val top5Subjects: List<GetTop5SubjectResponseModel> = emptyList(),
    val subjects: List<SubjectModel> = emptyList(),
    val studySets : List<GetStudySetResponseModel> = emptyList(),
    val folders : List<GetFolderResponseModel> = emptyList(),
    val classes : List<GetClassByOwnerResponseModel> = emptyList(),
)