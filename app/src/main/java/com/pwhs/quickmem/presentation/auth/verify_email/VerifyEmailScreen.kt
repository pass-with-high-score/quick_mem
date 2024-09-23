package com.pwhs.quickmem.presentation.auth.verify_email

import ResendOrLogoutText
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.presentation.auth.verify_email.components.ConfirmEmailText
import com.pwhs.quickmem.presentation.auth.verify_email.components.EmailCheckPromptText
import com.pwhs.quickmem.presentation.auth.verify_email.components.HighlightedEmailText
import com.pwhs.quickmem.presentation.auth.verify_email.components.Otp
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
    val uiState = viewModel.uiState.collectAsState()
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
            }

        }
    }
    VerifyEmail(
        modifier = modifier,
        email = email,
        onVerifyClick = {
            viewModel.onEvent(VerifyEmailUiAction.VerifyEmail)
        },
        onOtpChange = {
            viewModel.onEvent(VerifyEmailUiAction.OtpChange(it))
        },
        onEmailChange = {
            viewModel.onEvent(VerifyEmailUiAction.EmailChange(it))
        },
        onNavigationIconClick = {
            navigator.popBackStack()
        },
        onResendClick = {

        },
        onLogoutClick = {
            // logout click
        }
    )
}

@Composable
private fun VerifyEmail(
    modifier: Modifier = Modifier,
    email: String,
    onNavigationIconClick: () -> Unit = {},
    onVerifyClick: () -> Unit = {},
    onOtpChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onResendClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {
    Scaffold (
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
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
                ConfirmEmailText()
                Spacer(modifier = Modifier.height(16.dp))
                HighlightedEmailText(email = email)
                Spacer(modifier = Modifier.height(16.dp))
                EmailCheckPromptText()
            }

            Box(
                contentAlignment = Alignment.Center
            ){
                Otp(
                    count = 6,
                    error = error,
                    success = success,
                    errorColor = errorColor,
                    successColor = successColor,
                    focusedColor = Color.White,
                    unFocusedColor = Color.Gray,
                    onFinish = { otp->
                        onOtpChange(otp)
                        onEmailChange(email)
                    },
                    modifier=Modifier.size(50.dp,80.dp),
                )
            }

            AuthButton(
                text = "Done",
                onClick = onVerifyClick,
                modifier = Modifier.padding(top = 106.dp)
            )

            AuthButton(
                modifier = Modifier.padding(top = 16.dp, bottom = 20.dp),
                onClick = {

                },
                text = "Update email",
                colors = Color.White,
                textColor = colorScheme.onSurface,
            )
            ResendOrLogoutText(
                onResendClick = onResendClick,
                onLogoutClick = onLogoutClick
            )
        }
    }
}

fun String.splitAtIndex(index: Int): String {
    return this.chunked(index).joinToString("\n")
}


@Preview
@Composable
fun VerifyEmailScreenPreview() {
    VerifyEmail(email = "")
}