package com.pwhs.quickmem.presentation.auth.forgot_password.verify_email.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.pwhs.quickmem.presentation.auth.forgot_password.verify_email.ForgotPasswordVerifyEmailUiAction
import com.pwhs.quickmem.presentation.auth.forgot_password.verify_email.ForgotPasswordVerifyEmailUiEvent
import com.pwhs.quickmem.presentation.auth.forgot_password.verify_email.ForgotPasswordVerifyEmailViewModel
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>
fun ForgotPasswordVerifyEmailScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: ForgotPasswordVerifyEmailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                ForgotPasswordVerifyEmailUiEvent.None -> {
                    //
                }

                ForgotPasswordVerifyEmailUiEvent.ResetFailure -> {
                    Toast.makeText(
                        context,
                        "Email verification failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ForgotPasswordVerifyEmailUiEvent.ResetSuccess -> {
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

    ForgotPasswordVerifyEmail(
        modifier,
        onNavigationIconClick = {
            navigator.popBackStack()
        },
        email = uiState.value.email,
        emailError = uiState.value.emailError,
        onEmailChanged = { email ->
            viewModel.onEvent(ForgotPasswordVerifyEmailUiAction.EmailChanged(email))
        },
        onResetClick = {
            viewModel.onEvent(ForgotPasswordVerifyEmailUiAction.ResetPassword)
        }
    )
}

@Composable
private fun ForgotPasswordVerifyEmail(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    email: String = "",
    emailError: String = "",
    onEmailChanged: (String) -> Unit = {},
    onResetClick: () -> Unit = {}
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
                painter = painterResource(id = R.drawable.forgot_password_verify_email),
                contentDescription = "Forgot Password Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(220.dp)
            )
            Spacer(modifier = Modifier.height(26.dp))
            Text(
                text = "Forgot\n" +
                        "Your Password?",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            AuthTextField(
                value = email,
                onValueChange = onEmailChanged,
                label = "Email Adress",
                iconId = R.drawable.ic_email,
                contentDescription = "Email",
                type = TextFieldType.EMAIL,
                error = emailError
            )

            AuthButton(
                text = "Reset Password",
                onClick = onResetClick,
                modifier = Modifier.padding(top = 16.dp),
                colors = colorScheme.onSecondaryContainer,
                textColor = Color.White,
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewForgotPasswordVerifyEmailScreen() {
    ForgotPasswordVerifyEmail()
}
