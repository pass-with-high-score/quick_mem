package com.pwhs.quickmem.presentation.app.study_set.studies.write

sealed class LearnByWriteUiEvent {
    data object Finished : LearnByWriteUiEvent()
}