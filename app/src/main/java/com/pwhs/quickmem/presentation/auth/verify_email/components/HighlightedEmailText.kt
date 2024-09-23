package com.pwhs.quickmem.presentation.auth.verify_email.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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
            text = buildAnnotatedString {
                append("We have sent an email to\n")
                withStyle(style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )) {
                    append("$email. ")
                }
                append("Please enter the correct confirmation code from the email.")
            },
            style = LocalTextStyle.current.copy(
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        )
    }
}