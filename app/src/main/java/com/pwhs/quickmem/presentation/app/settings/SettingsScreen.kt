package com.pwhs.quickmem.presentation.app.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.app.settings.component.SettingCard
import com.pwhs.quickmem.presentation.app.settings.component.SettingItem
import com.pwhs.quickmem.presentation.app.settings.component.SettingSwitch
import com.pwhs.quickmem.presentation.app.settings.component.SettingTitleSection
import com.pwhs.quickmem.presentation.app.settings.component.SettingValidatePasswordBottomSheet
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ChangePasswordSettingScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UpdateEmailSettingScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UpdateFullNameSettingScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@Destination<RootGraph>
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel(),
    resultUpdateFullName: ResultRecipient<UpdateFullNameSettingScreenDestination, Boolean>
) {

    resultUpdateFullName.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.initData()
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SettingUiEvent.NavigateToLogin -> {
                    navigator.navigate(WelcomeScreenDestination) {
                        popUpTo(WelcomeScreenDestination) {
                            inclusive = true
                            launchSingleTop = true
                        }
                    }
                }

                SettingUiEvent.NavigateToChangeEmail -> {
                    navigator.navigate(
                        UpdateEmailSettingScreenDestination(
                            userId = uiState.userId,
                            email = uiState.email
                        )
                    )
                }

                SettingUiEvent.NavigateToChangeFullName -> {
                    navigator.navigate(
                        UpdateFullNameSettingScreenDestination(
                            userId = uiState.userId,
                            fullName = uiState.fullName
                        )
                    )
                }

                SettingUiEvent.NavigateToChangeUsername -> {
                    // TODO()
                }
            }

        }
    }

    Setting(
        modifier = modifier,
        fullName = uiState.fullName,
        username = uiState.username,
        email = uiState.email,
        password = uiState.password,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onChangePassword = {
            viewModel.onEvent(SettingUiAction.OnChangePassword(it))
        },
        onChangeType = {
            viewModel.onEvent(SettingUiAction.OnChangeType(it))
        },
        onNavigationBack = {
            navigator.navigateUp()
        },
        onSubmitClick = {
            viewModel.onEvent(SettingUiAction.OnSubmitClick)
        },
        onLogout = {
            viewModel.onEvent(SettingUiAction.Logout)
        },
        onNavigateToChangePassword = {
            navigator.navigate(ChangePasswordSettingScreenDestination(email = uiState.email))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(
    modifier: Modifier = Modifier,
    fullName: String = "",
    username: String = "",
    email: String = "",
    password: String = "",
    errorMessage: String = "",
    isLoading: Boolean = false,
    onChangePassword: (String) -> Unit = {},
    onChangeType: (SettingChangeValueEnum) -> Unit = {},
    onSubmitClick: () -> Unit = {},
    onNavigationBack: () -> Unit = {},
    onNavigateToChangePassword: () -> Unit = {},
    onNavigateToManageStorage: () -> Unit = {},
    onEnablePushNotifications: () -> Unit = {},
    onEnableSoundEffects: () -> Unit = {},
    onNavigateToPrivacyPolicy: () -> Unit = {},
    onNavigateToChangeLanguage: () -> Unit = {},
    onNavigateToTermsOfService: () -> Unit = {},
    onNavigateToOpenSourceLicenses: () -> Unit = {},
    onNavigateToHelpCenter: () -> Unit = {},
    onLogout: () -> Unit = {},
) {

    val bottomSheetState = rememberModalBottomSheetState()
    var showVerifyPasswordBottomSheet by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Settings", style = typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigationBack,
                    ) {
                        Icon(
                            imageVector = AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    SettingTitleSection(title = "Personal info")
                    SettingCard {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            SettingItem(
                                title = "Full name",
                                subtitle = fullName,
                                onClick = {
                                    showVerifyPasswordBottomSheet = true
                                    onChangeType(SettingChangeValueEnum.FULL_NAME)
                                }
                            )
                            HorizontalDivider()
                            SettingItem(
                                title = "Username",
                                subtitle = username,
                                onClick = {
                                    showVerifyPasswordBottomSheet = true
                                    onChangeType(SettingChangeValueEnum.USERNAME)
                                }
                            )
                            HorizontalDivider()
                            SettingItem(
                                title = "Email",
                                subtitle = email,
                                onClick = {
                                    showVerifyPasswordBottomSheet = true
                                    onChangeType(SettingChangeValueEnum.EMAIL)
                                }
                            )
                            HorizontalDivider()
                            SettingItem(
                                title = "Change password",
                                onClick = {
                                    onNavigateToChangePassword()
                                }
                            )
                        }
                    }
                }
                item {
                    SettingTitleSection(title = "Offline studying")
                    SettingCard {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            SettingSwitch(
                                title = "Save study sets for offline studying",
                                subtitle = "Your 8 most recently studied sets will be saved for offline studying",
                                value = true,
                                onChangeValue = {
                                    onEnablePushNotifications()
                                }
                            )
                            HorizontalDivider()
                            SettingItem(
                                title = "Manage storage",
                                onClick = {
                                    onNavigateToManageStorage()
                                }
                            )
                        }
                    }
                }
                item {
                    SettingTitleSection(title = "Preferences")
                    SettingCard {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            SettingItem(
                                title = "Language",
                                subtitle = "English",
                                onClick = {
                                    onNavigateToChangeLanguage()
                                }
                            )
                            HorizontalDivider()
                            SettingSwitch(
                                title = "Push notifications",
                                onChangeValue = {
                                    onEnablePushNotifications()
                                },
                                value = true
                            )
                            HorizontalDivider()
                            SettingSwitch(
                                title = "Sound effects",
                                onChangeValue = {
                                    onEnableSoundEffects()
                                },
                                value = true
                            )

                        }
                    }
                }
                item {
                    SettingTitleSection(title = "About")
                    SettingCard {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            SettingItem(
                                title = "Privacy policy",
                                onClick = {
                                    onNavigateToPrivacyPolicy()
                                }
                            )
                            HorizontalDivider()
                            SettingItem(
                                title = "Terms of service",
                                onClick = {
                                    onNavigateToTermsOfService()
                                }
                            )
                            HorizontalDivider()
                            SettingItem(
                                title = "Open source licenses",
                                onClick = {
                                    onNavigateToOpenSourceLicenses()
                                }
                            )
                            HorizontalDivider()
                            SettingItem(
                                title = "Help center",
                                onClick = {
                                    onNavigateToHelpCenter()
                                }
                            )
                        }
                    }
                }
                item {
                    SettingCard(
                        onClick = onLogout,
                        modifier = Modifier.padding(top = 30.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Icon(
                                    imageVector = AutoMirrored.Outlined.Logout,
                                    contentDescription = "Log out",
                                    modifier = Modifier.size(30.dp)
                                )
                                Text(
                                    "Log out", style = typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Icon(
                                imageVector = AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "Navigate to Welcome",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }

                    // Logo
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(40.dp)

                    )
                    Text(
                        text = "QuickMem", style = typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            SettingValidatePasswordBottomSheet(
                bottomSheetState = bottomSheetState,
                showVerifyPasswordBottomSheet = showVerifyPasswordBottomSheet,
                onDismissRequest = {
                    showVerifyPasswordBottomSheet = false
                    onChangePassword("")
                },
                password = password,
                onSubmitClick = {
                    onSubmitClick()
                },
                onChangePassword = onChangePassword,
                errorMessage = errorMessage
            )
            LoadingOverlay(
                isLoading = isLoading
            )
        }
    }
}

@PreviewLightDark
@Composable
fun SettingScreenPreview(modifier: Modifier = Modifier) {
    QuickMemTheme {
        Setting(modifier)
    }
}