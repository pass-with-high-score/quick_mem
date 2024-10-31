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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
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
        onNavigationBack = {
            navigator.navigateUp()
        },
        onNavigateToWelcome = {
            viewModel.onEvent(SettingUiAction.Logout)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(
    modifier: Modifier = Modifier,
    onNavigationBack: () -> Unit = {},
    onNavigateToWelcome: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Settings", style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigationBack,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedButton(
                onClick = onNavigateToWelcome,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                border = BorderStroke(
                    width = 0.5.dp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Logout,
                            contentDescription = "Log out",
                            modifier = Modifier.size(30.dp)
                        )
                        Text(
                            "Log out", style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Navigate to Welcome",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp)
            )
            Text(
                text = "QuickMem", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
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