package com.pwhs.quickmem.presentation.auth.signup

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.ramcosta.composedestinations.generated.destinations.SignupWithEmailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>
fun SignupScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: SignupViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
        topBar = {
            AuthTopAppBar(onClick = {
                navigator.navigate(WelcomeScreenDestination) {
                    popUpTo(LoginScreenDestination) {
                        inclusive = true
                        launchSingleTop = true
                    }
                }
            })
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
                text = "Sign up",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary
                )
            )

            AuthButton(
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    navigator.navigate(SignupWithEmailScreenDestination)
                },
                text = "Sign up with email",
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
                    text = "OR",
                    style = MaterialTheme.typography.bodyMedium.copy(color = colorScheme.onSurface)
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
                onClick = {
                    viewModel.signupWithGoogle()
                },
                text = "Continue with Google",
                colors = Color.White,
                textColor = colorScheme.onSurface,
                icon = R.drawable.ic_google
            )
            AuthButton(
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    viewModel.signupWithFacebook()
                },
                text = "Continue with Facebook",
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
                        append("By signing up, you agree to the")
                        withStyle(
                            style = SpanStyle(
                                color = colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(" Terms and Conditions")
                        }
                        append(" and the ")
                        withStyle(
                            style = SpanStyle(
                                color = colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(" Privacy Policy")
                        }
                        append(" of QuickMem")
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { },
                textAlign = TextAlign.Center
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = colorScheme.onSurface,
                            fontSize = 16.sp,
                        )
                    ) {
                        append("Already have an account?")
                        withStyle(
                            style = SpanStyle(
                                color = colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(" Log in")
                        }
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable {
                        navigator.navigate(LoginScreenDestination) {
                            popUpTo(LoginScreenDestination) {
                                inclusive = true
                                launchSingleTop = true
                            }
                        }
                    }
            )
        }
    }
}
