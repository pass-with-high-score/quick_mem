package com.pwhs.quickmem.presentation.auth.login.email.component

import androidx.compose.foundation.Image
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.util.gradientBackground
import kotlinx.coroutines.launch

@Composable
@Destination<RootGraph>
fun SendVerifyEmailScreen(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    onResendEmail: () -> Unit,
    email: String,
    message: String = "Your account is not verified. Please check your email for the verification link."
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
        topBar = {
            AuthTopAppBar(onClick = onNavigationIconClick)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.verfication_email),
                contentDescription = "Verification Email",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            AuthButton(
                onClick = {
                    coroutineScope.launch {
                        onResendEmail()
                    }
                },
                text = "Resend Verification Email",
                colors = Color.Black,
                textColor = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerificationScreenPreview() {
    SendVerifyEmailScreen(
        onResendEmail = { },
        email = "test@example.com", // Thêm email cho preview
        message = "Your account is not verified. Please check your email for the verification link."
    )
}
