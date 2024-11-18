package com.pwhs.quickmem.presentation.app.deeplink.folder

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
import com.ramcosta.composedestinations.generated.destinations.FolderDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(
    navArgs = LoadFolderArgs::class
)
@Composable
fun LoadFolderScreen(
    modifier: Modifier = Modifier,
    viewModel: LoadFolderViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoadFolderUiEvent.FolderLoaded -> {
                    navigator.navigate(
                        FolderDetailScreenDestination(
                            id = event.folderId,
                            code = uiState.folderCode,
                        )
                    ) {
                        popUpTo(NavGraphs.root) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                }

                is LoadFolderUiEvent.UnAuthorized -> {
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

                is LoadFolderUiEvent.Error -> {
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