package com.pwhs.quickmem.presentation.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LoginWithEmailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SignupScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WebViewAppDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@Destination<RootGraph>
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    Timber.d("Run here")
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                LoginUiEvent.LoginWithGoogle -> {
                    // open web view
                    navigator.navigate(
                        WebViewAppDestination(
                            oAuthLink = "https://api.quickmem.app/auth/google",
                        )
                    )
                }

                LoginUiEvent.LoginWithFacebook -> {
                    // open web view
                    navigator.navigate(
                        WebViewAppDestination(
                            oAuthLink = "https://api.quickmem.app/auth/facebook",
                        )
                    )
                }

                LoginUiEvent.LoginWithEmail -> {
                    navigator.navigate(LoginWithEmailScreenDestination)
                }

                LoginUiEvent.NavigateToSignUp -> {
                    navigator.navigate(SignupScreenDestination) {
                        popUpTo(LoginScreenDestination) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    Login(
        modifier = modifier,
        onNavigationIconClick = {
            navigator.navigate(WelcomeScreenDestination) {
                popUpTo(LoginScreenDestination) {
                    inclusive = true
                }
            }
        },
        onNavigateToSignup = {
            viewModel.onEvent(LoginUiAction.NavigateToSignUp)
        },
        onLoginWithEmail = {
            viewModel.onEvent(LoginUiAction.LoginWithEmail)
        },
        onLoginWithGoogle = {
            viewModel.onEvent(LoginUiAction.LoginWithGoogle)
        },
        onLoginWithFacebook = {
            viewModel.onEvent(LoginUiAction.LoginWithFacebook)
        }
    )
}

@Composable
fun Login(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    onNavigateToSignup: () -> Unit = {},
    onLoginWithEmail: () -> Unit = {},
    onLoginWithGoogle: () -> Unit = {},
    onLoginWithFacebook: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
        topBar = {
            AuthTopAppBar(
                onClick = onNavigationIconClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.log_in),
                contentDescription = "Login",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .size(200.dp)
            )

            Text(
                text = stringResource(R.string.tx_log_in),
                style = typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary
                )
            )

            AuthButton(
                modifier = Modifier.padding(top = 16.dp),
                onClick = onLoginWithEmail,
                text = stringResource(R.string.txt_log_in_with_email),
                colors = colorScheme.primary,
                textColor = Color.White,
                icon = R.drawable.ic_email
            )

            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(1.dp)
                        .background(colorScheme.onSurface)
                )
                Text(
                    text = stringResource(R.string.txt_or),
                    style = typography.bodyMedium.copy(color = colorScheme.onSurface)
                )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(1.dp)
                        .background(colorScheme.onSurface)
                )
            }

            AuthButton(
                modifier = Modifier.padding(top = 16.dp),
                onClick = onLoginWithGoogle,
                text = stringResource(R.string.tx_continue_with_google),
                colors = Color.White,
                textColor = colorScheme.onSurface,
                icon = R.drawable.ic_google,
            )
            AuthButton(
                modifier = Modifier.padding(top = 16.dp),
                onClick = onLoginWithFacebook,
                text = stringResource(R.string.txt_continue_with_facebook),
                colors = Color.White,
                textColor = colorScheme.onSurface,
                icon = R.drawable.ic_facebook
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = colorScheme.onSurface,
                            fontSize = 16.sp,
                        )
                    ) {
                        append(stringResource(R.string.txt_don_t_have_an_account))
                        withStyle(
                            style = SpanStyle(
                                color = colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(stringResource(R.string.txt_sign_up))
                        }
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable {
                        onNavigateToSignup()
                    }
            )
        }
    }
}
