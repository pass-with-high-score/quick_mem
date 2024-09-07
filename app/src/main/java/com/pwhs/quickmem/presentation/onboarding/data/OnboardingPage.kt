package com.pwhs.quickmem.presentation.onboarding.data

import androidx.annotation.DrawableRes
import com.pwhs.quickmem.R

data class OnboardingPage(
    @DrawableRes val imageRes: Int,
    val title: String,
    val description: String
)

val onboardingPagesList = listOf(
    OnboardingPage(
        imageRes = R.drawable.onboarding1,
        title = "Welcome to QuickMem",
        description = "QuickMem is a flashcard app that helps you learn anything."
    ),
    OnboardingPage(
        imageRes = R.drawable.onboarding2,
        title = "Create your own flashcards",
        description = "You can create your own flashcards and share them with others."
    ),
    OnboardingPage(
        imageRes = R.drawable.onboarding3,
        title = "Learn with spaced repetition",
        description = "QuickMem uses spaced repetition to help you remember what you learn."
    )
)