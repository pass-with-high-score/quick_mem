package com.pwhs.quickmem.presentation.onboarding.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.pwhs.quickmem.R

data class OnboardingPage(
    @DrawableRes val imageRes: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
)

val onboardingPagesList = listOf(
    OnboardingPage(
        imageRes = R.drawable.onboarding1,
        title = R.string.txt_onboarding_title1,
        description = R.string.txt_onboarding_description1
    ),
    OnboardingPage(
        imageRes = R.drawable.onboarding2,
        title = R.string.txt_onboarding_title2,
        description = R.string.txt_onboarding_description2
    ),
    OnboardingPage(
        imageRes = R.drawable.onboarding3,
        title = R.string.txt_onboarding_title3,
        description = R.string.txt_onboarding_description3
    )
)