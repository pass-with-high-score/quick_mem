package com.pwhs.quickmem.presentation.auth.verify_email

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.auth.verify_email.components.OtpInputField
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>
fun VerifyEmailScreen(
    modifier: Modifier = Modifier,
    viewModel: VerifyEmailViewModel = hiltViewModel(),
    email: String,
    navigator: DestinationsNavigator,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                VerifyEmailUiEvent.None -> {
                    // Do nothing
                }

                VerifyEmailUiEvent.VerifyFailure -> {
                    Toast.makeText(context, "Failed to verify email", Toast.LENGTH_SHORT).show()
                }

                VerifyEmailUiEvent.VerifySuccess -> {
                    navigator.popBackStack()
                    navigator.navigate(HomeScreenDestination) {
                        popUpTo(HomeScreenDestination) {
                            inclusive = true
                            launchSingleTop = true
                        }
                    }
                }

                VerifyEmailUiEvent.ResendFailure -> {
                    Toast.makeText(context, "Failed to resend email", Toast.LENGTH_SHORT).show()
                }

                VerifyEmailUiEvent.ResendSuccess -> {
                    Toast.makeText(context, "Please check your email again", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }
    VerifyEmail(
        modifier = modifier,
        otp = uiState.otp,
        email = uiState.email,
        countdownTime = uiState.countdown,
        onVerifyClick = {
            viewModel.onEvent(VerifyEmailUiAction.VerifyEmail)
        },
        onOtpChange = {
            viewModel.onEvent(VerifyEmailUiAction.OtpChange(it))
        },
        onResendClick = {
            viewModel.onEvent(VerifyEmailUiAction.ResendEmail(email))
        },
        onLogoutClick = {
            navigator.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VerifyEmail(
    modifier: Modifier = Modifier,
    email: String = "",
    otp: String = "",
    countdownTime: Int = 0,
    onVerifyClick: () -> Unit = {},
    onOtpChange: (String) -> Unit = {},
    onResendClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                actions = {
                    IconButton(
                        onClick = onLogoutClick
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = "Logout",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onLogoutClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            val error by remember {
                mutableStateOf(false)
            }
            val success by remember {
                mutableStateOf(false)
            }

            val bgColor by animateColorAsState(
                (if (success) successColor else if (error) errorColor else Color.White).copy(alpha = .2f),
                label = "bgColor"
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.otpimage),
                    contentDescription = "Send OTP",
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .size(100.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "OTP Verification",
                    style = typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    ),
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    buildAnnotatedString {
                        append("Enter the OTP sent to ")
                        append("\n")
                        withStyle(
                            style = SpanStyle(
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(email)
                        }
                    },
                    style = typography.bodyMedium.copy(
                        fontSize = 16.sp,
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            Box(
                contentAlignment = Alignment.Center
            ) {
                OtpInputField(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .padding(bottom = 20.dp),
                    otpText = otp,
                    shouldCursorBlink = false,
                    onOtpModified = { value, otpFilled ->
                        onOtpChange(value)
                        if (otpFilled) {
                            keyboardController?.hide()
                        }
                    }
                )
            }

            Text(
                text = buildAnnotatedString {
                    append("Code expires in: ")
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append(String.format("%02d:%02d", countdownTime / 60, countdownTime % 60))
                    }
                },
                style = typography.bodyMedium.copy(
                    fontSize = 16.sp,
                ),
            )

            AuthButton(
                text = "Verify",
                onClick = onVerifyClick,
                modifier = Modifier.padding(top = 20.dp, bottom = 15.dp)
            )

            Text(
                text = "Didn't receive the code?",
                style = typography.bodyMedium.copy(
                    fontSize = 16.sp,
                ),
                modifier = Modifier.padding(bottom = 2.dp)
            )

            TextButton(
                onClick = onResendClick,
            ) {
                Text(
                    text = "Resend code",
                    style = typography.bodyMedium.copy(
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
fun VerifyEmailScreenPreview() {
    VerifyEmail(email = "")
}