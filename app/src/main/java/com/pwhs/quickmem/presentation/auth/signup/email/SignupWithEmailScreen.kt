package com.pwhs.quickmem.presentation.auth.signup.email

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.enums.TextFieldType
import com.pwhs.quickmem.core.data.enums.UserRole
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.auth.component.AuthTextField
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.presentation.auth.signup.email.component.DatePickerModalInput
import com.pwhs.quickmem.presentation.auth.signup.email.component.RadioGroup
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.util.gradientBackground
import com.pwhs.quickmem.util.isDateSmallerThan
import com.pwhs.quickmem.util.toFormattedString
import com.pwhs.quickmem.util.toTimestamp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.VerifyEmailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>
fun SignupWithEmailScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: SignupWithEmailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                SignUpWithEmailUiEvent.SignUpFailure -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.txt_sign_up_failure),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                SignUpWithEmailUiEvent.SignUpSuccess -> {
                    navigator.navigate(
                        VerifyEmailScreenDestination(
                            email = uiState.value.email,
                            isFromSignup = true,
                            resetPasswordToken = ""
                        )
                    )
                }
            }

        }
    }
    SignupWithEmail(
        modifier,
        isLoading = uiState.value.isLoading,
        onNavigationIconClick = {
            navigator.popBackStack()
        },
        email = uiState.value.email,
        emailError = uiState.value.emailError,
        onEmailChanged = { email ->
            viewModel.onEvent(SignUpWithEmailUiAction.EmailChanged(email))
        },
        password = uiState.value.password,
        passwordError = uiState.value.passwordError,
        onPasswordChanged = { password ->
            viewModel.onEvent(SignUpWithEmailUiAction.PasswordChanged(password))
        },
        birthday = uiState.value.birthday,
        birthdayError = uiState.value.birthdayError,
        onBirthdayChanged = { birthday ->
            viewModel.onEvent(SignUpWithEmailUiAction.BirthdayChanged(birthday))
        },
        onRoleChanged = { role ->
            viewModel.onEvent(SignUpWithEmailUiAction.UserRoleChanged(role))
        },
        onSignUpClick = {
            viewModel.onEvent(SignUpWithEmailUiAction.SignUp)
        }
    )
}

@Composable
private fun SignupWithEmail(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onNavigationIconClick: () -> Unit = {},
    email: String = "",
    emailError: String = "",
    onEmailChanged: (String) -> Unit = {},
    password: String = "",
    passwordError: String = "",
    onPasswordChanged: (String) -> Unit = {},
    birthday: String = "",
    birthdayError: String = "",
    onBirthdayChanged: (String) -> Unit = {},
    onRoleChanged: (UserRole) -> Unit = {},
    onSignUpClick: () -> Unit = {}
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
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                )

                Text(
                    text = stringResource(R.string.txt_signup_with_email),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.primary
                    ),
                    modifier = Modifier.padding(16.dp)
                )
                AuthTextField(
                    value = birthday,
                    onValueChange = onBirthdayChanged,
                    label = stringResource(R.string.txt_select_your_birthday),
                    iconId = R.drawable.ic_calendar,
                    contentDescription = "Birthday",
                    readOnly = true,
                    enabled = false,
                    onClick = { isDatePickerVisible = true },
                    type = TextFieldType.BIRTHDAY,
                    error = birthdayError
                )
                AuthTextField(
                    value = email,
                    onValueChange = onEmailChanged,
                    label = stringResource(R.string.txt_example_email_com),
                    iconId = R.drawable.ic_email,
                    contentDescription = "Email",
                    type = TextFieldType.EMAIL,
                    error = emailError
                )
                AuthTextField(
                    value = password,
                    onValueChange = onPasswordChanged,
                    label = stringResource(R.string.txt_create_your_password),
                    iconId = R.drawable.ic_lock,
                    contentDescription = stringResource(R.string.txt_password),
                    type = TextFieldType.PASSWORD,
                    error = passwordError,
                    imeAction = ImeAction.Done,
                    onDone = onSignUpClick
                )

                if (isRoleVisible) {
                    RadioGroup(
                        modifier = Modifier.fillMaxWidth(),
                        onRoleChanged = onRoleChanged
                    )
                }

                AuthButton(
                    text = stringResource(R.string.txt_sign_up),
                    onClick = onSignUpClick,
                    modifier = Modifier.padding(top = 16.dp)
                )

            }
            LoadingOverlay(
                isLoading = isLoading,
                text = stringResource(R.string.txt_signing_up)
            )
        }
    }

    if (isDatePickerVisible) {
        DatePickerModalInput(
            onDateSelected = {
                if (it != null) {
                    onBirthdayChanged(it.toFormattedString())
                    isRoleVisible = !it.isDateSmallerThan()
                }
                isDatePickerVisible = false
            },
            onDismiss = {
                isDatePickerVisible = false
            },
            initialDate = birthday.toTimestamp()
        )
    }
}

@Preview
@Composable
fun PreviewSignupWithEmailScreen() {
    SignupWithEmail()
}