package com.pwhs.quickmem.presentation.app.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.app.settings.component.SettingTitleSection
import com.pwhs.quickmem.presentation.app.settings.component.SettingItem
import com.pwhs.quickmem.presentation.app.settings.component.SettingSwitch
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel()
) {
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
            }

        }
    }

    Setting(
        modifier = modifier,
        fullName = uiState.fullName,
        username = uiState.username,
        email = uiState.email,
        onNavigationBack = {
            navigator.navigateUp()
        },
        onLogout = {
            viewModel.onEvent(SettingUiAction.Logout)
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
    onNavigationBack: () -> Unit = {},
    onNavigateToEditFullName: () -> Unit = {},
    onNavigateToEditUsername: () -> Unit = {},
    onNavigateToEditEmail: () -> Unit = {},
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                SettingTitleSection(title = "Personal info")
                Card(
                    border = BorderStroke(
                        width = 1.dp,
                        color = colorScheme.onSurface.copy(alpha = 0.12f)
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.surface
                    ),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        SettingItem(
                            title = "Full name",
                            subtitle = fullName,
                            onClick = {
                                onNavigateToEditFullName()
                            }
                        )
                        HorizontalDivider()
                        SettingItem(
                            title = "Username",
                            subtitle = username,
                            onClick = {
                                onNavigateToEditUsername()
                            }
                        )
                        HorizontalDivider()
                        SettingItem(
                            title = "Email",
                            subtitle = email,
                            onClick = {
                                onNavigateToEditEmail()
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
                Card(
                    border = BorderStroke(
                        width = 1.dp,
                        color = colorScheme.onSurface.copy(alpha = 0.12f)
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.surface
                    ),
                ) {
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
                Card(
                    border = BorderStroke(
                        width = 1.dp,
                        color = colorScheme.onSurface.copy(alpha = 0.12f)
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.surface
                    ),
                ) {
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
                Card(
                    border = BorderStroke(
                        width = 1.dp,
                        color = colorScheme.onSurface.copy(alpha = 0.12f)
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.surface
                    ),
                ) {
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
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = MaterialTheme.shapes.large,
                    border = BorderStroke(
                        width = 1.dp,
                        color = colorScheme.onSurface.copy(alpha = 0.12f)
                    ),
                    onClick = onLogout,
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.surface
                    ),
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
                        .size(50.dp)

                )
                Text(
                    text = "QuickMem", style = typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
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