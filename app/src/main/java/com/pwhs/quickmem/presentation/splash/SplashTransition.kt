package com.pwhs.quickmem.presentation.splash

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SplashScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.ramcosta.composedestinations.utils.destination
import timber.log.Timber
// For test
object SplashTransition : DestinationStyle.Animated() {
    override val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? =
        {
            Timber.d("SplashTransition enterTransition")
            when (initialState.destination()) {
                SplashScreenDestination -> {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(700)
                    )
                }

                WelcomeScreenDestination -> {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(700)
                    )
                }

                HomeScreenDestination ->
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(700)
                    )

                else -> null
            }
        }
}