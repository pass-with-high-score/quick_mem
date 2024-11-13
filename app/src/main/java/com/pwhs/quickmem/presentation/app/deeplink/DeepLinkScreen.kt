package com.pwhs.quickmem.presentation.app.deeplink

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.annotation.parameters.DeepLink
import com.ramcosta.composedestinations.generated.destinations.ClassDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FolderDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

//Deeplink for receiving code from class, folder, study set
//TODO: Implement this screen
@Destination<RootGraph>(
    deepLinks = [
        DeepLink(uriPattern = "quickmem://join/class?code={classCode}"),
        DeepLink(uriPattern = "quickmem://share/folder?code={folderCode}"),
        DeepLink(uriPattern = "quickmem://share/studyset?code={studySetCode}")
    ]
)
@Composable
fun DeepLinkScreen(
    modifier: Modifier = Modifier,
    studySetCode: String? = null,
    folderCode: String? = null,
    classCode: String? = null,
    navigator: DestinationsNavigator,
    viewModel: DeepLinkViewModel = hiltViewModel(),
) {
    Timber.d("DeepLinkScreen: studySetCode: $studySetCode, folderCode: $folderCode, classCode: $classCode")
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(DeepLinkUiAction.TriggerEvent(studySetCode, folderCode, classCode))
    }
    LaunchedEffect(key1 = viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is DeepLinkUiEvent.JoinClass -> {
                    Timber.d("DeepLinkUiEvent.JoinClass: ${event.classCode}")
                    navigator.navigate(
                        ClassDetailScreenDestination(
                            code = event.classCode,
                            id = "",
                            description = "",
                            title = "",
                        )
                    )
                }

                is DeepLinkUiEvent.ShareFolder -> {
                    navigator.navigate(
                        FolderDetailScreenDestination(
                            id = "",
                            code = event.folderCode,
                        )
                    )
                }

                is DeepLinkUiEvent.ShareStudySet -> {
                    navigator.navigate(
                        StudySetDetailScreenDestination(
                            id = "",
                            code = event.studySetCode,
                        )
                    )
                }

                DeepLinkUiEvent.UnAuthorized -> {
                    navigator.navigateUp()
                    navigator.navigate(WelcomeScreenDestination)
                }
            }

        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            Text(text = stringResource(R.string.txt_deeplinkscreen))
        }
    }
}