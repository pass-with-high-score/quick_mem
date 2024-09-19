package com.pwhs.quickmem.presentation.auth.forgot_password.verify_email

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
@Destination<RootGraph>
fun ForgotPasswordVerifyEmailScreen(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {}) {
    val viewModel: ForgotPasswordVerifyEmailViewModel = viewModel()
    val email by viewModel.email.collectAsState()
    val emailError by viewModel.emailError.collectAsState()

    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
        topBar = {
            AuthTopAppBar(onClick = onNavigationIconClick)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Image(
                painter = painterResource(id = R.drawable.forgot_password_verify_email),
                contentDescription = "Forgot Password Image",
                modifier = Modifier.size(220.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Forgot your password?",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(26.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    viewModel.onEmailChanged(it)
                },
                label = { Text(text = "Email Address") },
                isError = emailError,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .padding(horizontal = 16.dp)
            )

            if (emailError) {
                Text(
                    text = "Please enter a valid email address",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AuthButton(
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .height(48.dp),
                onClick = {
                    viewModel.resetPassword()
                },
                text = "Reset Password",
                colors = colorScheme.onSecondaryContainer,
                textColor = Color.White,
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ForgotPasswordVerifyEmailScreenPreview() {
    ForgotPasswordVerifyEmailScreen()
}
