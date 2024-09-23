package com.pwhs.quickmem.presentation.auth.forgot_password.set_new_password.component

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
import androidx.compose.material3.MaterialTheme.typography
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
import com.pwhs.quickmem.presentation.auth.forgot_password.set_new_password.SetNewPasswordUiAction
import com.pwhs.quickmem.presentation.auth.forgot_password.set_new_password.SetNewPasswordUiEvent
import com.pwhs.quickmem.presentation.auth.forgot_password.set_new_password.SetNewPasswordViewModel
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>
fun SetNewPasswordScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: SetNewPasswordViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                SetNewPasswordUiEvent.None -> {
                    //
                }

                SetNewPasswordUiEvent.ResetFailure -> {
                    Toast.makeText(
                        context,
                        "Password reset failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                SetNewPasswordUiEvent.ResetSuccess -> {
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

    SetNewPassword(
        modifier,
        onNavigationIconClick = {
            navigator.popBackStack()
        },
        password = uiState.value.password,
        confirmPassword = uiState.value.confirmPassword,
        passwordError = uiState.value.passwordError,
        confirmPasswordError = uiState.value.confirmPasswordError,
        onPasswordChanged = { password ->
            viewModel.onEvent(SetNewPasswordUiAction.PasswordChanged(password))
        },
        onConfirmPasswordChanged = { confirmPassword ->
            viewModel.onEvent(SetNewPasswordUiAction.ConfirmPasswordChanged(confirmPassword))
        },
        onSubmitClick = {
            viewModel.onEvent(SetNewPasswordUiAction.Submit)
        }
    )
}

@Composable
private fun SetNewPassword(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    password: String = "",
    confirmPassword: String = "",
    passwordError: String = "",
    confirmPasswordError: String = "",
    onPasswordChanged: (String) -> Unit = {},
    onConfirmPasswordChanged: (String) -> Unit = {},
    onSubmitClick: () -> Unit = {}
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
                painter = painterResource(id = R.drawable.forgot_password_verify_password),
                contentDescription = "Forgot Password Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp)
            )

            Text(
                text = "Please enter your new password",
                style = typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(26.dp))

            AuthTextField(
                value = password,
                onValueChange = onPasswordChanged,
                label = "New Password",
                iconId = R.drawable.ic_lock,
                contentDescription = "New Password",
                type = TextFieldType.PASSWORD,
                error = passwordError
            )

            AuthTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChanged,
                label = "Confirm Password",
                iconId = R.drawable.ic_lock,
                contentDescription = "Confirm Password",
                type = TextFieldType.PASSWORD,
                error = confirmPasswordError
            )

            AuthButton(
                text = "Done",
                onClick = onSubmitClick,
                modifier = Modifier.padding(top = 16.dp),
                colors = colorScheme.onSecondaryContainer,
                textColor = Color.White
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewForgotPasswordVerifyPasswordScreen() {
    SetNewPassword()
}
