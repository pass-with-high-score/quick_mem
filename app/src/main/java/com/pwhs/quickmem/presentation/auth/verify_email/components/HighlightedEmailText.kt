package com.pwhs.quickmem.presentation.auth.verify_email.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun HighlightedEmailText(
    email: String = ""
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter the OTP sent to $email",
            style = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        )
    }
}