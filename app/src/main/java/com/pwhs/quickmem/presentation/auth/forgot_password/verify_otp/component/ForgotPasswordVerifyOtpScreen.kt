package com.pwhs.quickmem.presentation.auth.forgot_password.verify_otp.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.TextFieldType
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.auth.component.AuthTextField
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.presentation.auth.forgot_password.verify_otp.ForgotPasswordVerifyOtpUiAction
import com.pwhs.quickmem.presentation.auth.forgot_password.verify_otp.ForgotPasswordVerifyOtpUiEvent
import com.pwhs.quickmem.presentation.auth.forgot_password.verify_otp.ForgotPasswordVerifyOtpViewModel
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SetNewPasswordScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>
fun ForgotPasswordVerifyOtpScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: ForgotPasswordVerifyOtpViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                ForgotPasswordVerifyOtpUiEvent.None -> {
                    //
                }

                ForgotPasswordVerifyOtpUiEvent.VerifyFailure -> {
                    Toast.makeText(
                        context,
                        "Verification failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ForgotPasswordVerifyOtpUiEvent.VerifySuccess -> {
                    navigator.popBackStack()
                    navigator.navigate(SetNewPasswordScreenDestination) {
                        popUpTo(SetNewPasswordScreenDestination) {
                            inclusive = true
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }

    ForgotPasswordVerifyOtp(
        modifier,
        onNavigationIconClick = {
            navigator.popBackStack()
        },
        code = uiState.value.otp,
        codeError = uiState.value.otpError,
        onCodeChanged = { code ->
            viewModel.onEvent(ForgotPasswordVerifyOtpUiAction.OtpChanged(code))
        },
        onVerifyClick = {
            viewModel.onEvent(ForgotPasswordVerifyOtpUiAction.VerifyOtp)
        }
    )
}

@Composable
private fun ForgotPasswordVerifyOtp(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    code: String = "",
    codeError: String = "",
    onCodeChanged: (String) -> Unit = {},
    onVerifyClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
        topBar = {
            AuthTopAppBar(onClick = onNavigationIconClick)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 40.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.forgot_password_verify_code),
                contentDescription = "Forgot Password Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
            )
            Spacer(modifier = Modifier.height(26.dp))
            Text(
                text = "Email has been sent\n" +
                        "Please enter the OTP sent to your email",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            AuthTextField(
                value = code,
                onValueChange = onCodeChanged,
                label = "OTP",
                iconId = R.drawable.ic_code,
                contentDescription = "OTP",
                type = TextFieldType.TEXT,
                error = codeError
            )

            AuthButton(
                text = "Send",
                onClick = onVerifyClick,
                modifier = Modifier.padding(top = 16.dp),
                colors = colorScheme.onSecondaryContainer,
                textColor = Color.White,
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewForgotPasswordVerifyCodeScreen() {
    ForgotPasswordVerifyOtp()
}




