package com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.component.LicenseItem
import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.component.OpenSourceTopAppBar
import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.data.OpenSourceLicensesData
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.OpenSourceDetailDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun OpenSourceScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: OpenSourceViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is OpenSourceUiEvent.NavigateToDetail -> {
                    navigator.navigate(OpenSourceDetailDestination(licenseId = event.license.id))
                }

                is OpenSourceUiEvent.ShowError -> {
                    event.message
                }
            }
        }
    }

    OpenSourceLicensesUI(
        modifier = modifier,
        license = state.licenses,
        onClickSource = { licenseId ->
            viewModel.onEvent(OpenSourceUiAction.LicenseClicked(licenseId))
        },
        onNavigateBack = {
            navigator.navigateUp()
        },
    )
}

@Composable
fun OpenSourceLicensesUI(
    modifier: Modifier = Modifier,
    license: List<OpenSourceLicensesData> = emptyList(),
    onClickSource: (String) -> Unit = {},
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            OpenSourceTopAppBar(
                title = "Open Source Licenses",
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding)
        ) {
            items(license) { licenses ->
                LicenseItem(
                    license = licenses,
                    onClickItem = onClickSource
                )
            }
        }
    }
}

