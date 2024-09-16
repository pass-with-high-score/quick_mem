package com.pwhs.quickmem.presentation.auth.signup.email

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.UserRole
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.util.gradientBackground
import com.pwhs.quickmem.util.isDateSmallerThan
import com.pwhs.quickmem.util.toDateFormatted
import com.pwhs.quickmem.util.toFormattedString
import com.pwhs.quickmem.util.upperCaseFirstLetter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber


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
                SignUpWithEmailUiEvent.None -> TODO()
                SignUpWithEmailUiEvent.SignUpFailure -> {
                    Toast.makeText(
                        context,
                        "Sign up failure",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                SignUpWithEmailUiEvent.SignUpSuccess -> {
                    Toast.makeText(
                        context,
                        "Sign up success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }
    SignupWithEmail(
        modifier,
        onNavigationIconClick = {
            navigator.popBackStack()
        },
        email = uiState.value.email,
        onEmailChanged = { email ->
            viewModel.onEvent(SignUpWithEmailUiAction.EmailChanged(email))
        },
        password = uiState.value.password,
        onPasswordChanged = { password ->
            viewModel.onEvent(SignUpWithEmailUiAction.PasswordChanged(password))
        },
        birthday = uiState.value.birthday,
        onBirthdayChanged = { birthday ->
            viewModel.onEvent(SignUpWithEmailUiAction.BirthdayChanged(birthday))
        },
        role = uiState.value.userRole,
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
    onNavigationIconClick: () -> Unit = {},
    email: String = "",
    onEmailChanged: (String) -> Unit = {},
    password: String = "",
    onPasswordChanged: (String) -> Unit = {},
    birthday: String = "",
    onBirthdayChanged: (String) -> Unit = {},
    role: UserRole = UserRole.STUDENT,
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp)
            )

            Text(
                text = "Signup with email",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary
                ),
                modifier = Modifier.padding(16.dp)
            )

            TextField(
                value = birthday,
                onValueChange = onBirthdayChanged,
                label = { Text("Birthday") },
                readOnly = true,
                enabled = false,
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Birthday",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(colorScheme.onSurface)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isDatePickerVisible = true
                    }
            )
            TextField(
                value = email,
                onValueChange = onEmailChanged,
                label = { Text("Email") },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "Email",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(colorScheme.onSurface)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()

            )
            TextField(
                value = password,
                onValueChange = onPasswordChanged,
                label = { Text("Password") },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Password",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(colorScheme.onSurface)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            if (!isRoleVisible) {
                RadioGroup(
                    modifier = Modifier.fillMaxWidth(),
                    onRoleChanged = onRoleChanged
                )
            }

            ElevatedButton(
                onClick = onSignUpClick,
            ) {
                Text("Sign up")
            }

        }
    }

    if (isDatePickerVisible) {
        DatePickerModalInput(
            onDateSelected = {
                if (it != null) {
                    onBirthdayChanged(it.toFormattedString())
                    Timber.d("Less than 18: ${it.isDateSmallerThan()}")
                    isRoleVisible = it.isDateSmallerThan()
                }
                isDatePickerVisible = false
            },
            onDismiss = {
                isDatePickerVisible = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
private fun RadioGroup(
    modifier: Modifier = Modifier,
    onRoleChanged: (UserRole) -> Unit = {}
) {
    val options = listOf(UserRole.TEACHER, UserRole.STUDENT)
    val selectedOption = rememberSaveable { mutableStateOf(options[0]) }
    Column(
        modifier = modifier.padding(vertical = 16.dp),
    ) {
        Text(
            text = "Are you a teacher or a student?",
            style = MaterialTheme.typography.bodyLarge,
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption.value == option,
                        onClick = {
                            selectedOption.value = option
                            onRoleChanged(option)
                        }
                    )
                    Text(
                        text = option.role.upperCaseFirstLetter(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSignupWithEmailScreen() {
    SignupWithEmail()
}