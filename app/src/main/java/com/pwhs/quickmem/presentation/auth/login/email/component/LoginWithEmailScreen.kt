package com.pwhs.quickmem.presentation.auth.login.email.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.TextFieldType
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.auth.component.AuthTextField
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.presentation.auth.login.email.LoginWithEmailViewModel
import com.pwhs.quickmem.presentation.auth.login.email.LoginWithEmailUiAction
import com.pwhs.quickmem.presentation.auth.login.email.LoginWithEmailUiEvent
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SendVerifyEmailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>
fun LoginWithEmailScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: LoginWithEmailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                LoginWithEmailUiEvent.None -> {}
                LoginWithEmailUiEvent.LoginFailure -> {
                    Toast.makeText(context, "Login failure", Toast.LENGTH_SHORT).show()
                }

                LoginWithEmailUiEvent.LoginSuccess -> {
                    Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show()
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

    LoginWithEmail(
        modifier = modifier,
        onNavigationIconClick = { navigator.popBackStack() },
        email = uiState.email,
        emailError = uiState.emailError,
        onEmailChanged = { email -> viewModel.onEvent(LoginWithEmailUiAction.EmailChanged(email)) },
        password = uiState.password,
        passwordError = uiState.passwordError,
        onPasswordChanged = { password ->
            viewModel.onEvent(LoginWithEmailUiAction.PasswordChanged(password))
        },
        onLoginClick = { viewModel.onEvent(LoginWithEmailUiAction.Login) },
        onForgotPasswordClick = {
            navigator.navigate(SendVerifyEmailScreenDestination) {
                popUpTo(SendVerifyEmailScreenDestination) {
                    inclusive = true
                    launchSingleTop = true
                }
            }
        }
    )
}


@Composable
private fun LoginWithEmail(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    email: String = "",
    emailError: String = "",
    onEmailChanged: (String) -> Unit = {},
    password: String = "",
    passwordError: String = "",
    onPasswordChanged: (String) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
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
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp)
            )

            Text(
                text = "Login with email",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(16.dp)
            )

            AuthTextField(
                value = email,
                onValueChange = onEmailChanged,
                label = "Email",
                iconId = R.drawable.ic_email,
                contentDescription = "Email",
                type = TextFieldType.EMAIL,
                error = emailError
            )

            AuthTextField(
                value = password,
                onValueChange = onPasswordChanged,
                label = "Password",
                iconId = R.drawable.ic_lock,
                contentDescription = "Password",
                type = TextFieldType.PASSWORD,
                error = passwordError
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextButton(
                    onClick = onForgotPasswordClick
                ) {
                    Text(
                        text = "Forgot password?",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF2d333d),
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.End
                    )
                }
            }

            AuthButton(
                text = "Log in",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 18.dp),
                onClick = {
                    if (emailError.isEmpty() && passwordError.isEmpty()) {
                        onLoginClick()
                    }
                },
                colors = if (emailError.isEmpty() && passwordError.isEmpty()) Color(0xFF2d333d) else Color(
                    0xFFf3f4f6
                ),
                borderColor = if (emailError.isEmpty() && passwordError.isEmpty()) Color(0xFF2d333d) else Color(
                    0xFFf3f4f6
                ),
                textColor = if (emailError.isEmpty() && passwordError.isEmpty()) Color.White else Color(
                    0xFF9095a0
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoginWithEmailScreen() {
    LoginWithEmail()
}
