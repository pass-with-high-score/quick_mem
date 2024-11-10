package com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.data.SourceLicensesList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenSourceViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(OpenSourceUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<OpenSourceUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        onEvent(OpenSourceUiAction.LoadLicenses)
    }

    fun onEvent(event: OpenSourceUiAction) {
        when (event) {
            is OpenSourceUiAction.LicenseClicked -> handleLicenseClick(event.licenseId)
            OpenSourceUiAction.LoadLicenses -> loadLicenses()
        }
    }

    private fun loadLicenses() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                licenses = SourceLicensesList,
                isLoading = false
            )
        }
    }

    private fun handleLicenseClick(licenseId: String) {
        viewModelScope.launch {
            val clickedLicense = _uiState.value.licenses.find { it.id == licenseId }
            if (clickedLicense != null && clickedLicense.linkSource.isNotEmpty()) {
                _uiEvent.send(OpenSourceUiEvent.NavigateToDetail(clickedLicense))
            } else {
                _uiEvent.send(OpenSourceUiEvent.ShowError("License link not available"))
            }
        }
    }

}
