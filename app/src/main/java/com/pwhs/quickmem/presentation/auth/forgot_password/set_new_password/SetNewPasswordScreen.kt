package com.pwhs.quickmem.presentation.auth.forgot_password.set_new_password

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.enums.TextFieldType
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.auth.component.AuthTextField
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.LoginWithEmailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>(
    navArgs = SetNewPasswordArgs::class
)
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
                SetNewPasswordUiEvent.ResetFailure -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.txt_password_reset_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                SetNewPasswordUiEvent.ResetSuccess -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.txt_password_reset_successful),
                        Toast.LENGTH_SHORT
                    ).show()
                    navigator.popBackStack()
                    navigator.navigate(LoginWithEmailScreenDestination) {
                        popUpTo(NavGraphs.root) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
            }
        }
    }

    SetNewPassword(
        modifier,
        isLoading = uiState.value.isLoading,
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
    isLoading: Boolean = false,
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
        Box {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(top = 40.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.txt_please_enter_your_new_password),
                    style = typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(26.dp))

                AuthTextField(
                    value = password,
                    onValueChange = onPasswordChanged,
                    label = stringResource(R.string.txt_new_password),
                    iconId = R.drawable.ic_lock,
                    contentDescription = stringResource(R.string.txt_new_password),
                    type = TextFieldType.PASSWORD,
                    error = passwordError
                )

                AuthTextField(
                    value = confirmPassword,
                    onValueChange = onConfirmPasswordChanged,
                    label = stringResource(R.string.txt_confirm_password),
                    iconId = R.drawable.ic_lock,
                    contentDescription = stringResource(R.string.txt_confirm_password),
                    type = TextFieldType.PASSWORD,
                    error = confirmPasswordError,
                    imeAction = ImeAction.Done,
                    onDone = onSubmitClick
                )

                AuthButton(
                    text = stringResource(R.string.txt_done),
                    onClick = onSubmitClick,
                    modifier = Modifier.padding(top = 16.dp),
                    colors = colorScheme.primary,
                    textColor = Color.White
                )
            }
            LoadingOverlay(
                isLoading = isLoading
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewForgotPasswordVerifyPasswordScreen() {
    QuickMemTheme {
        SetNewPassword()
    }
}
