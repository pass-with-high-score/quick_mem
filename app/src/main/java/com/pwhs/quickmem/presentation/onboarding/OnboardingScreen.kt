package com.pwhs.quickmem.presentation.onboarding

import OnboardingPageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.onboarding.component.OnboardingButton
import com.pwhs.quickmem.presentation.onboarding.component.OnboardingIndicator
import com.pwhs.quickmem.presentation.onboarding.data.onboardingPagesList
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Composable
@Destination<RootGraph>
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {


    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val pagerState = rememberPagerState(pageCount = { onboardingPagesList.size })
            val coroutineScope = rememberCoroutineScope()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )

                OnboardingButton(
                    text = "Skip",
                    onClick = {
                        viewModel.saveIsFirstRun(false)
                        navigator.popBackStack()
                        navigator.navigate(WelcomeScreenDestination)
                    },
                    backgroundColor = Color.White,
                    borderColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.primary,
                    buttonShape = CircleShape,
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp),
                    showIcon = false
                )
            }

            HorizontalPager(state = pagerState) { page ->
                OnboardingPageView(page = onboardingPagesList[page])
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(onboardingPagesList.size) { index ->
                    OnboardingIndicator(isSelected = pagerState.currentPage == index)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (pagerState.currentPage != onboardingPagesList.size - 1) {
                    OnboardingButton(
                        text = "Next",
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        showIcon = true
                    )
                } else {
                    OnboardingButton(
                        text = "Get Started",
                        onClick = {
                            viewModel.saveIsFirstRun(false)
                            navigator.popBackStack()
                            navigator.navigate(WelcomeScreenDestination)
                        }
                    )
                }
            }
        }
    }
}