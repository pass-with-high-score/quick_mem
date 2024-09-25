package com.pwhs.quickmem.presentation.app.study_set.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StudySetDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    init {
        val id: String = savedStateHandle["id"] ?: ""
        Timber.d("StudySetDetailViewModel: $id")
    }
}