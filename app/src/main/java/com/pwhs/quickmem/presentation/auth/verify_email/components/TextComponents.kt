package com.pwhs.quickmem.presentation.auth.verify_email.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmEmailText() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(
            text = "OTP Verification",
            style = LocalTextStyle.current.copy(
                fontSize = 30.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun EmailCheckPromptText() {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 40.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(
            text = "Didn't get an email? Check your ",
            style = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        BasicText(
            text = "spam folder or request another email.",
            style = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        )
    }
}