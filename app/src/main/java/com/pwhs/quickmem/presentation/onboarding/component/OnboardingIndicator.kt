package com.pwhs.quickmem.presentation.onboarding.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun OnboardingIndicator(isSelected: Boolean) {
    val width: Dp by animateDpAsState(
        targetValue = if (isSelected) 30.dp else 15.dp,
        label = "Indicator"
    )
    val color =
        if (isSelected) colorScheme.primary else colorScheme.inversePrimary

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(width = width, height = 15.dp)
            .background(color, shape = shapes.small)
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingIndicatorPreview() {
    QuickMemTheme {
        Row {
            OnboardingIndicator(isSelected = true)
            OnboardingIndicator(isSelected = false)
            OnboardingIndicator(isSelected = false)
        }
    }
}
