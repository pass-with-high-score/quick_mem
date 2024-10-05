package com.pwhs.quickmem.presentation.auth.social

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.pwhs.quickmem.core.data.TextFieldType
import com.pwhs.quickmem.core.data.UserRole
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.auth.component.AuthTextField
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.presentation.auth.signup.email.component.DatePickerModalInput
import com.pwhs.quickmem.presentation.auth.signup.email.component.RadioGroup
import com.pwhs.quickmem.util.gradientBackground
import com.pwhs.quickmem.util.isDateSmallerThan
import com.pwhs.quickmem.util.toFormattedString
import com.pwhs.quickmem.util.toTimestamp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.annotation.parameters.DeepLink
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber


@Destination<RootGraph>(
    deepLinks = [
        DeepLink(uriPattern = "quickmem://oauth/google/callback?token={token}&email={email}&fullName={fullName}&provider={provider}&picture={picture}"),
        DeepLink(uriPattern = "quickmem://oauth/facebook/callback?token={token}&email={email}&fullName={fullName}&provider={provider}&picture={picture}"),
    ]
)
@Composable
fun AuthSocialScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthSocialViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    email: String = "",
    token: String = "",
    fullName: String = "",
    provider: String = "",
    picture: String = "",
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(email, token, fullName, picture, provider) {
        Timber.d("Run here")
        Timber.d("Email: $email")
        Timber.d("Token: $token")
        Timber.d("Full name: $fullName")
        Timber.d("Provider: $provider")
        Timber.d("Picture: $picture")
        viewModel.initDataDeeplink(email, fullName, picture, token, provider)
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is AuthSocialUiEvent.OnAvatarUrlChanged -> {
                    Timber.d("Avatar url: ${event.avatarUrl}")
                    uiState.copy(avatarUrl = event.avatarUrl)
                }
                is AuthSocialUiEvent.OnBirthDayChanged -> {
                    Timber.d("Birthday: ${event.birthDay}")
                }
                is AuthSocialUiEvent.OnEmailChanged -> {
                    Timber.d("Email: ${event.email}")
                    uiState.copy(email = event.email)
                }
                is AuthSocialUiEvent.OnNameChanged -> {
                    Timber.d("Name: ${event.name}")
                    uiState.copy(fullName = event.name)
                }
                is AuthSocialUiEvent.OnRoleChanged -> {
                    Timber.d("Role: ${event.role}")
                }
                AuthSocialUiEvent.Register -> {
                    Timber.d("Register")
                }
                AuthSocialUiEvent.None -> {
                    // Do nothing
                }
                AuthSocialUiEvent.SignUpFailure -> {
                    Toast.makeText(
                        context,
                        "Sign up failure",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                AuthSocialUiEvent.SignUpSuccess -> {
                    Timber.d("Sign up success")
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

    AuthSocial(
        modifier = modifier,
        onNavigationIconClick = {
            navigator.popBackStack()
        },
        onRegisterClick = {
            viewModel.onEvent(AuthSocialUiAction.Register)
        },
        birthday = uiState.birthDay,
        birthdayError = uiState.birthdayError,
        onBirthdayChanged = { birthday ->
            viewModel.onEvent(AuthSocialUiAction.OnBirthDayChanged(birthday))
        },
        onRoleChanged = { role ->
            viewModel.onEvent(AuthSocialUiAction.OnRoleChanged(role))
        },
    )
}

@Composable
fun AuthSocial(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    birthday: String = "",
    birthdayError: String = "",
    onBirthdayChanged: (String) -> Unit = {},
    onRoleChanged: (UserRole) -> Unit = {},
) {
    var isDatePickerVisible by rememberSaveable { mutableStateOf(false) }
    var isRoleVisible by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
        topBar = {
            AuthTopAppBar(
                onClick = onNavigationIconClick,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 40.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                text = "Almost done!",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary
                )
            )

            Text(
                text = "Enter your birthday. This won't be visible to others.",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 16.sp,
                    color = colorScheme.primary
                ),
                modifier = Modifier.padding(top = 16.dp, bottom = 22.dp)
            )

            AuthTextField(
                value = birthday,
                onValueChange = onBirthdayChanged,
                label = "Select your birthday",
                iconId = R.drawable.ic_calendar,
                contentDescription = "Birthday",
                readOnly = true,
                enabled = false,
                onClick = { isDatePickerVisible = true },
                type = TextFieldType.BIRTHDAY,
                error = birthdayError
            )

            if (isRoleVisible) {
                RadioGroup(
                    modifier = Modifier.fillMaxWidth(),
                    onRoleChanged = onRoleChanged
                )
            }

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
                            append("Privacy Policy")
                        }
                        append(" of QuickMem")
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { },
            )

            if (isDatePickerVisible) {
                DatePickerModalInput(
                    onDateSelected = {
                        if (it != null) {
                            onBirthdayChanged(it.toFormattedString())
                            Timber.d("Less than 18: ${it.isDateSmallerThan()}")
                            isRoleVisible = !it.isDateSmallerThan()
                            Timber.d("isRoleVisible: $isRoleVisible")
                        }
                        isDatePickerVisible = false
                    },
                    onDismiss = {
                        isDatePickerVisible = false
                    },
                    initialDate = birthday.toTimestamp()
                )
            }

            AuthButton(
                text = "Sign up",
                onClick = onRegisterClick,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSignupWithGoogleScreen() {
    AuthSocial()
}