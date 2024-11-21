package com.pwhs.quickmem.presentation.app.study_set.studies.true_false

sealed class LearnByTrueFalseUiEvent {
    data object Finished : LearnByTrueFalseUiEvent()
}