package com.pwhs.quickmem.presentation.app.settings

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.MaterialTheme.colorScheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.enums.LanguageCode
import com.pwhs.quickmem.presentation.app.settings.component.SettingCard
import com.pwhs.quickmem.presentation.app.settings.component.SettingItem
import com.pwhs.quickmem.presentation.app.settings.component.SettingSwitch
import com.pwhs.quickmem.presentation.app.settings.component.SettingTitleSection
import com.pwhs.quickmem.presentation.app.settings.component.SettingValidatePasswordBottomSheet
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.presentation.component.QuickMemAlertDialog
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.toFormattedString
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.ChangeLanguageScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ChangePasswordSettingScreenDestination
import com.ramcosta.composedestinations.generated.destinations.OpenSourceScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UpdateEmailSettingScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UpdateFullNameSettingScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UpdateUsernameSettingScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.revenuecat.purchases.CustomerInfo

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Destination<RootGraph>
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel(),
    resultUpdateFullName: ResultRecipient<UpdateFullNameSettingScreenDestination, Boolean>,
    resultUpdateEmail: ResultRecipient<UpdateEmailSettingScreenDestination, Boolean>,
    resultChangePassword: ResultRecipient<ChangePasswordSettingScreenDestination, Boolean>,
    resultChangeLanguage: ResultRecipient<ChangeLanguageScreenDestination, Boolean>,
    resultUpdateUsername: ResultRecipient<UpdateUsernameSettingScreenDestination, Boolean>
) {
    val context = LocalContext.current

    resultUpdateFullName.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(SettingUiAction.Refresh)
                }
            }
        }
    }

    resultUpdateEmail.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(SettingUiAction.Refresh)
                }
            }
        }
    }

    resultChangePassword.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(SettingUiAction.Refresh)
                }
            }
        }
    }

    resultChangeLanguage.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(SettingUiAction.Refresh)
                }
            }
        }
    }

    resultUpdateUsername.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(SettingUiAction.Refresh)
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
                        popUpTo(NavGraphs.root) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = false
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
                    navigator.navigate(
                        UpdateUsernameSettingScreenDestination(
                            userId = uiState.userId,
                            username = uiState.username
                        )
                    )
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
        isPushNotificationsEnabled = uiState.isPushNotificationsEnabled,
        isAppPushNotificationsEnabled = uiState.isAppPushNotificationsEnabled,
        languageCode = uiState.languageCode,
        onChangePassword = {
            viewModel.onEvent(SettingUiAction.OnChangePassword(it))
        },
        onChangeType = {
            viewModel.onEvent(SettingUiAction.OnChangeType(it))
        },
        onNavigationBack = {
            navigator.navigateUp()
        },
        onNavigateToOpenSourceLicenses = {
            navigator.navigate(OpenSourceScreenDestination)
        },
        onNavigateToHelpCenter = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/pass-with-high-score/quick_mem/issues")
            )
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                e.stackTrace
            }
        },
        onSubmitClick = {
            viewModel.onEvent(SettingUiAction.OnSubmitClick)
        },
        onLogout = {
            viewModel.onEvent(SettingUiAction.Logout)
        },
        onNavigateToChangePassword = {
            navigator.navigate(ChangePasswordSettingScreenDestination(email = uiState.email))
        },
        onEnablePushNotifications = {
            viewModel.onEvent(SettingUiAction.OnChangePushNotifications(!uiState.isPushNotificationsEnabled))
        },
        onNotificationEnabled = {
            viewModel.onEvent(SettingUiAction.OnChangeAppPushNotifications(it))
        },
        onNavigateToChangeLanguage = {
            navigator.navigate(ChangeLanguageScreenDestination())
        },
        onNavigateToPrivacyPolicy = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://pass-with-high-score.github.io/QuickMem-Services/")
            )
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                e.stackTrace
            }
        },
        onNavigateToTermsOfService = {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://pass-with-high-score.github.io/QuickMem-Services/")
            )
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                e.stackTrace
            }
        },
        customerInfo = uiState.customerInfo
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun Setting(
    modifier: Modifier = Modifier,
    fullName: String = "",
    username: String = "",
    email: String = "",
    password: String = "",
    errorMessage: String = "",
    isLoading: Boolean = false,
    isPushNotificationsEnabled: Boolean = false,
    isAppPushNotificationsEnabled: Boolean = false,
    languageCode: LanguageCode = LanguageCode.EN,
    onChangePassword: (String) -> Unit = {},
    onChangeType: (SettingChangeValueEnum) -> Unit = {},
    onSubmitClick: () -> Unit = {},
    onNavigationBack: () -> Unit = {},
    onNavigateToChangePassword: () -> Unit = {},
    onNavigateToManageStorage: () -> Unit = {},
    onEnablePushNotifications: (Boolean) -> Unit = {},
    onNotificationEnabled: (Boolean) -> Unit = {},
    onEnableSoundEffects: () -> Unit = {},
    onNavigateToPrivacyPolicy: () -> Unit = {},
    onNavigateToChangeLanguage: () -> Unit = {},
    onNavigateToTermsOfService: () -> Unit = {},
    onNavigateToOpenSourceLicenses: () -> Unit = {},
    onNavigateToHelpCenter: () -> Unit = {},
    onLogout: () -> Unit = {},
    customerInfo: CustomerInfo? = null
) {

    val bottomSheetState = rememberModalBottomSheetState()
    var showVerifyPasswordBottomSheet by remember {
        mutableStateOf(false)
    }
    val notificationPermission =
        rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
    LaunchedEffect(notificationPermission) {
        if (!notificationPermission.status.isGranted) {
            notificationPermission.launchPermissionRequest()
            onNotificationEnabled(false)
        } else {
            onNotificationEnabled(true)
        }
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.txt_settings),
                        style = typography.titleMedium.copy(
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
                            contentDescription = stringResource(R.string.txt_back),
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
                    SettingTitleSection(title = stringResource(R.string.txt_subscription))
                    SettingCard {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            SettingItem(
                                title = stringResource(R.string.txt_expiration_date),
                                subtitle = customerInfo?.latestExpirationDate?.toFormattedString()
                                    ?: stringResource(R.string.txt_no_subscription),
                            )
                            SettingItem(
                                title = stringResource(R.string.txt_plan),
                                subtitle = when (customerInfo?.activeSubscriptions?.firstOrNull()
                                    .toString()) {
                                    "quickmem_plus:yearly-plan" -> stringResource(R.string.txt_quickmem_plus_yearly)
                                    "quickmem_plus:monthly-plan" -> stringResource(R.string.txt_quickmem_plus_monthly)

                                    else -> {
                                        stringResource(R.string.txt_no_subscription)
                                    }
                                }
                            )
                            customerInfo?.managementURL?.let {
                                SettingItem(
                                    title = stringResource(R.string.txt_manage_subscription),
                                    subtitle = stringResource(R.string.txt_click_here),
                                    onClick = {
                                        val browserIntent =
                                            Intent(Intent.ACTION_VIEW, it)
                                        context.startActivity(browserIntent)
                                    }
                                )
                            }
                        }
                    }
                }
                item {
                    SettingTitleSection(title = stringResource(R.string.txt_personal_info))
                    SettingCard {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            SettingItem(
                                title = stringResource(R.string.txt_full_name),
                                subtitle = fullName,
                                onClick = {
                                    showVerifyPasswordBottomSheet = true
                                    onChangeType(SettingChangeValueEnum.FULL_NAME)
                                }
                            )
                            HorizontalDivider()
                            SettingItem(
                                title = stringResource(R.string.txt_username),
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
                                title = stringResource(R.string.txt_change_password),
                                onClick = {
                                    onNavigateToChangePassword()
                                }
                            )
                        }
                    }
                }
                item {
                    SettingTitleSection(title = stringResource(R.string.txt_offline_studying))
                    SettingCard {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            SettingSwitch(
                                title = stringResource(R.string.txt_save_study_sets_for_offline_studying),
                                subtitle = stringResource(R.string.txt_your_8_most_recently_studied_sets_will_be_saved_for_offline_studying),
                                value = true,
                                onChangeValue = {
                                    // TODO(): Implement this feature
                                }
                            )
                            HorizontalDivider()
                            SettingItem(
                                title = stringResource(R.string.txt_manage_storage),
                                onClick = {
                                    onNavigateToManageStorage()
                                }
                            )
                        }
                    }
                }
                item {
                    SettingTitleSection(title = stringResource(R.string.txt_preferences))
                    SettingCard {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            SettingItem(
                                title = stringResource(R.string.txt_language),
                                subtitle = when (languageCode) {
                                    LanguageCode.EN -> stringResource(id = R.string.txt_english_us)
                                    LanguageCode.VI -> stringResource(id = R.string.txt_vietnamese)
                                },
                                onClick = {
                                    onNavigateToChangeLanguage()
                                }
                            )
                            HorizontalDivider()
                            SettingSwitch(
                                title = stringResource(R.string.txt_push_notifications),
                                onChangeValue = {
                                    if (!isAppPushNotificationsEnabled && !notificationPermission.status.isGranted) {
                                        showDialog = true
                                    } else {
                                        onEnablePushNotifications(it)
                                        onNotificationEnabled(it)
                                    }
                                },
                                value = isPushNotificationsEnabled && isAppPushNotificationsEnabled
                            )
                            HorizontalDivider()
                            SettingSwitch(
                                title = stringResource(R.string.txt_sound_effects),
                                onChangeValue = {
                                    onEnableSoundEffects()
                                },
                                value = true
                            )

                        }
                    }
                }
                item {
                    SettingTitleSection(title = stringResource(R.string.txt_about))
                    SettingCard {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            SettingItem(
                                title = stringResource(R.string.txt_settings_privacy_policy),
                                onClick = {
                                    onNavigateToPrivacyPolicy()
                                }
                            )
                            HorizontalDivider()
                            SettingItem(
                                title = stringResource(R.string.txt_settings_terms_of_service),
                                onClick = {
                                    onNavigateToTermsOfService()
                                }
                            )
                            HorizontalDivider()
                            SettingItem(
                                title = stringResource(R.string.txt_settings_open_source_licenses),
                                onClick = {
                                    onNavigateToOpenSourceLicenses()
                                }
                            )
                            HorizontalDivider()
                            SettingItem(
                                title = stringResource(R.string.txt_settings_help_center),
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
                                    contentDescription = stringResource(R.string.txt_settings_log_out),
                                    modifier = Modifier.size(30.dp)
                                )
                                Text(
                                    text = stringResource(R.string.txt_settings_log_out),
                                    style = typography.bodyLarge.copy(
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
                        text = stringResource(R.string.app_name),
                        style = typography.titleSmall.copy(
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
            if (showDialog) {
                QuickMemAlertDialog(
                    onDismissRequest = { showDialog = false },
                    onConfirm = {
                        showDialog = false
                        val intent =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                    putExtra(
                                        Settings.EXTRA_APP_PACKAGE,
                                        context.packageName
                                    )
                                }
                            } else {
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data =
                                        Uri.parse("package:${context.packageName}")
                                }
                            }

                        context.startActivity(intent)
                    },
                    title = stringResource(R.string.txt_push_notifications),
                    text = stringResource(R.string.txt_you_need_to_enable_push_notifications_in_the_app_settings),
                    confirmButtonTitle = stringResource(R.string.txt_open_settings),
                    dismissButtonTitle = stringResource(R.string.txt_cancel),
                    buttonColor = colorScheme.primary
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@PreviewLightDark
@Composable
fun SettingScreenPreview(modifier: Modifier = Modifier) {
    QuickMemTheme {
        Setting(modifier)
    }
}