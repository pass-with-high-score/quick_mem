package com.pwhs.quickmem.presentation.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.OnboardingScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay


@Destination<RootGraph>(
    start = true,
    style = SplashTransition::class,
)
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is SplashUiState.IsLoggedIn -> {
                navigator.navigate(OnboardingScreenDestination) {
                    popUpTo(OnboardingScreenDestination) {
                        inclusive = true
                        launchSingleTop = true
                    }
                }
            }

            is SplashUiState.FirstRun -> {
                navigator.navigate(OnboardingScreenDestination) {

                    popUpTo(WelcomeScreenDestination) {
                        inclusive = true
                        launchSingleTop = true

                    }
                }
            }

            is SplashUiState.NotFirstRun -> {
                navigator.navigate(WelcomeScreenDestination) {
                    popUpTo(WelcomeScreenDestination) {
                        inclusive = true
                        launchSingleTop = true
                    }
                }
            }

            else -> Unit
        }
    }

    Scaffold(
        modifier = modifier.gradientBackground()
    ) {
        Column(modifier = Modifier.padding(it)) { }
    }
}