package com.pwhs.quickmem.presentation.app.deeplink

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.annotation.parameters.DeepLink
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.JoinClassScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
) {
    LaunchedEffect(key1 = true) {
        when {
            classCode != null -> {
                navigator.navigate(
                    JoinClassScreenDestination(
                        code = classCode,
                        type = "class"
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
