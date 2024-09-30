package com.pwhs.quickmem.presentation.auth.social

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pwhs.quickmem.core.data.AuthProvider
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.annotation.parameters.DeepLink
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
    picture: String = ""
) {
    val uiState by viewModel.uiState.collectAsState()
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
                is AuthSocialUiEvent.OnAvatarUrlChanged -> TODO()
                is AuthSocialUiEvent.OnBirthDayChanged -> TODO()
                is AuthSocialUiEvent.OnEmailChanged -> TODO()
                is AuthSocialUiEvent.OnNameChanged -> TODO()
                is AuthSocialUiEvent.OnRoleChanged -> TODO()
                AuthSocialUiEvent.Register -> TODO()
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
        email = uiState.email,
        token = uiState.token,
        fullName = uiState.fullName,
        picture = uiState.avatarUrl,
        provider = uiState.provider ?: AuthProvider.google
    )
}

@Composable
fun AuthSocial(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    email: String = "",
    token: String = "",
    fullName: String = "",
    picture: String = "",
    provider: AuthProvider
) {
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
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onRegisterClick
            ) {
                Text("Register")
            }
            AsyncImage(
                model = picture,
                contentDescription = "Hello",
                onError = {
                    Timber.e("Load error")
                }
            )
            Text(email)
            Text(fullName)
            Text(provider.name)
            Text(token)
        }
    }
}