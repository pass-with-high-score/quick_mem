package com.pwhs.quickmem.presentation.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.ui.theme.Abrilfatface_Font
import com.pwhs.quickmem.util.splashBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.OnboardingScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay


@Destination<RootGraph>(start = true)
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
                navigator.navigate(HomeScreenDestination) {
                    popUpTo(HomeScreenDestination) {
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
        modifier = modifier.splashBackground(),
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedText(modifier = Modifier)
        }
    }
}

@Composable
fun AnimatedText(modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(2000)),
        exit = fadeOut(animationSpec = tween(2000))
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_bear),
                contentDescription = "Quick Mem Logo",
                modifier = modifier
                    .size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "QUICK MEM",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = Abrilfatface_Font,
                textAlign = TextAlign.Center,
                modifier = modifier
            )
        }
    }
}

