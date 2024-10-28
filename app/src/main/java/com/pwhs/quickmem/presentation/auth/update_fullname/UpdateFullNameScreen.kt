package com.pwhs.quickmem.presentation.auth.update_fullname

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun UpdateFullNameScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateFullNameViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UpdateFullNameUIEvent.UpdateSuccess -> {
                    navigator.navigate(HomeScreenDestination) {
                        popUpTo(HomeScreenDestination) {
                            inclusive = true
                        }
                    }
                }

                is UpdateFullNameUIEvent.ShowError -> {
                    Toast.makeText(
                        context,
                        uiState.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    UpdateFullName(
        modifier,
        fullName = uiState.fullName,
        isLoading = uiState.isLoading,
        onNameChanged = { name ->
            viewModel.onEvent(UpdateFullNameUIAction.FullNameChanged(name))
        },
        onSubmitClick = {
            viewModel.onEvent(UpdateFullNameUIAction.Submit)
        },
        onSkipClick = {
            navigator.navigate(HomeScreenDestination) {
                popUpTo(HomeScreenDestination) {
                    inclusive = true
                }
            }
        },
        errorMessage = uiState.errorMessage
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateFullName(
    modifier: Modifier = Modifier,
    fullName: String = "",
    onNameChanged: (String) -> Unit = {},
    onSubmitClick: () -> Unit = {},
    onSkipClick: () -> Unit = {},
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                title = {
                    Text(
                        text = "What should we call you?",
                        style = typography.titleMedium.copy(
                            fontSize = 18.sp
                        )
                    )
                },
                actions = {
                    TextButton(
                        onClick = onSkipClick
                    ) {
                        IconButton(onClick = onSkipClick) {
                            Icon(
                                Icons.Filled.Close,
                                tint = colorScheme.primary,
                                contentDescription = "Close",
                            )
                        }
                    }
                }
            )
        }
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = fullName,
                    onValueChange = onNameChanged,
                    label = { Text("Full Name") },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(top = 140.dp)
                        .padding(vertical = 16.dp),
                    shape = shapes.medium,
                    supportingText = {
                        errorMessage?.let {
                            Text(
                                text = it,
                                style = typography.bodyMedium.copy(
                                    color = colorScheme.error,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    },
                    colors = colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledTextColor = colorScheme.onSurface,
                        disabledPlaceholderColor = colorScheme.onSurface,
                        focusedTextColor = colorScheme.onSurface,
                        focusedPlaceholderColor = colorScheme.onSurface,
                        cursorColor = colorScheme.onSurface,
                        errorContainerColor = Color.Transparent,
                    ),
                )
                AuthButton(
                    text = "Submit",
                    onClick = onSubmitClick,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(48.dp)
                )
            }

            LoadingOverlay(
                isLoading = isLoading,
                modifier = Modifier.fillMaxSize(),
                text = "Updating...",
            )
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewSetName() {
    QuickMemTheme(
        darkTheme = false
    ) {
        UpdateFullName()
    }
}


