package com.pwhs.quickmem.presentation.app.deeplink

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.annotation.parameters.DeepLink
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.JoinClassScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LoadFolderScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LoadStudySetScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(
    deepLinks = [
        DeepLink(uriPattern = "quickmem://join/class?code={classCode}"),
        DeepLink(uriPattern = "quickmem://share/folder?code={folderCode}"),
        DeepLink(uriPattern = "quickmem://share/study-set?code={studySetCode}")
    ]
)
@Composable
fun DeepLinkScreen(
    studySetCode: String? = null,
    folderCode: String? = null,
    classCode: String? = null,
    navigator: DestinationsNavigator,
) {
    LaunchedEffect(key1 = true) {
        when {
            classCode != null -> {
                navigator.navigate(
                    JoinClassScreenDestination(
                        code = classCode,
                        isFromDeepLink = true
                    )
                ) {
                    popUpTo(NavGraphs.root) {
                        saveState = false
                    }
                    launchSingleTop = true
                    restoreState = false
                }
            }

            folderCode != null -> {
                navigator.navigate(
                    LoadFolderScreenDestination(
                        folderCode = folderCode
                    )
                ) {
                    popUpTo(NavGraphs.root) {
                        saveState = false
                    }
                    launchSingleTop = true
                    restoreState = false
                }
            }

            studySetCode != null -> {
                navigator.navigate(
                    LoadStudySetScreenDestination(
                        studySetCode = studySetCode
                    )
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
