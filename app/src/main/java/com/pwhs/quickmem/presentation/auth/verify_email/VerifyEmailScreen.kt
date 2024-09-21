package com.pwhs.quickmem.presentation.auth.verify_email

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.presentation.auth.verify_email.components.Otp
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Composable
@Destination<RootGraph>
fun VerifyEmailScreen(
    modifier: Modifier = Modifier,
    viewModel: VerifyEmailViewModel = hiltViewModel(),
    email: String,
    onNavigationIconClick: () -> Unit = { /* Handle back navigation */ }
) {
    VerifyEmail(
        modifier = modifier,
        email = email,
        onNavigationIconClick = onNavigationIconClick,
        onVerifyClick = {
            // Verify email

        }
    )
}

@Composable
private fun VerifyEmail(
    modifier: Modifier = Modifier,
    email: String,
    onNavigationIconClick: () -> Unit = {},
    onVerifyClick: () -> Unit = {}
) {
    Scaffold (
        modifier = modifier.gradientBackground(),
        containerColor = Color.White,
        topBar = {
            AuthTopAppBar(
                onClick = onNavigationIconClick,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val successColor = Color(0xff17917a)
            val errorColor = Color(0xFFFF6969)

            var error by remember {
                mutableStateOf(false)
            }
            var success by remember {
                mutableStateOf(false)
            }

            val bgColor by animateColorAsState((if (success) successColor else if (error) errorColor else Color.White).copy(alpha = .2f))

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text1()
                Spacer(modifier = Modifier.height(16.dp))
                HighlightedEmailText(email = email)
                Spacer(modifier = Modifier.height(16.dp))
                Text3()
            }

            Box(
                modifier=Modifier.background(bgColor),
                contentAlignment = Alignment.Center
            ){
                Otp(
                    count = 6,
                    error = error,
                    success = success,
                    errorColor = errorColor,
                    successColor = successColor,
                    focusedColor = Color(0xff313131),
                    unFocusedColor = Color.Gray,
                    onFinish = { otp->
                        Log.d("OTP", otp)
                        Log.d("Email", email)
                    },
                    modifier=Modifier.size(50.dp,80.dp),
                )
            }

            AuthButton(
                text = "Done",
                onClick = onVerifyClick,
                modifier = Modifier.padding(top = 156.dp)
            )

            AuthButton(
                modifier = Modifier.padding(top = 16.dp, bottom = 20.dp),
                onClick = {
                    // update email
                },
                text = "Update email",
                colors = Color.White,
                textColor = colorScheme.onSurface,
            )
            ResendOrLogoutText()
        }
    }
}

@Composable
fun Text1() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(
            text = "Confirm your email",
            style = LocalTextStyle.current.copy(
                fontSize = 30.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        BasicText(
            text = "to continue",
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



@Composable
fun Text3() {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 40.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(
            text = "Didn't get an email? Check your spam folder ",
            style = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        BasicText(
            text = "or request another email.",
            style = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        )
    }
}


@Composable
fun ResendOrLogoutText() {
    BasicText(
        text = buildAnnotatedString {
            append("Haven't received the email? ")
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("Resend")
            }
            append(" or ")
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("log out")
            }
            append(".")
        },
        style = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    )
}

fun String.splitAtIndex(index: Int): String {
    return this.chunked(index).joinToString("\n")
}


@Preview
@Composable
fun VerifyEmailScreenPreview() {
    VerifyEmail(email = "")
}