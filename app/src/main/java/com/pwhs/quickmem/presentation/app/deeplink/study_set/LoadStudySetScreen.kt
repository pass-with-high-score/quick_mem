package com.pwhs.quickmem.presentation.app.deeplink.study_set

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(
    navArgs = LoadStudySetArgs::class
)
@Composable
fun LoadStudySetScreen(
    modifier: Modifier = Modifier,
    viewModel: LoadStudySetViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoadStudySetUiEvent.StudySetLoaded -> {
                    navigator.navigate(
                        StudySetDetailScreenDestination(
                            id = event.studySetId,
                            code = uiState.studySetCode,
                        )
                    ) {
                        popUpTo(NavGraphs.root) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                }

                is LoadStudySetUiEvent.UnAuthorized -> {
                    navigator.navigate(
                        WelcomeScreenDestination()
                    ) {
                        popUpTo(NavGraphs.root) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                }

                is LoadStudySetUiEvent.NotFound -> {
                    navigator.navigate(
                        HomeScreenDestination()
                    ) {
                        popUpTo(NavGraphs.root) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LoadingOverlay(isLoading = uiState.isLoading)
    }
}