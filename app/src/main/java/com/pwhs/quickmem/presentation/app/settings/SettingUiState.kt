package com.pwhs.quickmem.presentation.app.settings

import com.pwhs.quickmem.core.data.alarm.StudyAlarm
import com.revenuecat.purchases.CustomerInfo
import java.time.LocalDateTime

data class SettingUiState(
    val canChangeInfo: Boolean = false,
    val isPlaySound: Boolean = false,
    val password: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val showBottomSheet: Boolean = false,
    val userId: String = "",
    val fullName: String = "",
    val username: String = "",
    val email: String = "",
    val role: String = "",
    val changeType: SettingChangeValueEnum = SettingChangeValueEnum.NONE,
    val isPushNotificationsEnabled: Boolean = false,
    val isAppPushNotificationsEnabled: Boolean = false,
    val customerInfo: CustomerInfo? = null,
    val studyAlarm: StudyAlarm = StudyAlarm(
        time = LocalDateTime.now(),
        message = "Study time"
    ),
    val isStudyAlarmEnabled: Boolean = false,
    val timeStudyAlarm: String = ""
)
