package com.pwhs.quickmem.presentation.app.explore

import androidx.annotation.StringRes

sealed class ExploreUiEvent {
    data class Error(@StringRes val message: Int) : ExploreUiEvent()
    data class EarnedCoins(@StringRes val message: Int) : ExploreUiEvent()
    data class CreatedStudySet(val studySetId: String) : ExploreUiEvent()
}