package com.pwhs.quickmem.presentation.auth.social

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.annotation.parameters.DeepLink
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber


@Destination<RootGraph>(
    deepLinks = [
        DeepLink(uriPattern = "quickmem://oauth/google/callback?token={token}&email={email}&firstName={firstName}&lastName={lastName}&picture={picture}"),
        DeepLink(uriPattern = "quickmem://oauth/google/callback?token={token}&email={email}&firstName={firstName}&lastName={lastName}&picture={picture}"),
    ]
)
@Composable
fun AuthSocialScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthSocialViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    email: String = "",
    token: String = "",
    firstName: String = "",
    lastName: String = "",
    picture: String = ""
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(email, token, firstName, lastName, picture) {
        val fullName = "$firstName $lastName"
        viewModel.initDataDeeplink(email, fullName, picture, token)
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
        email = uiState.email,
        token = uiState.token,
        firstName = uiState.name,
        lastName = uiState.name,
        picture = uiState.avatarUrl
    )
}

@Composable
fun AuthSocial(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit = {},
    email: String = "",
    token: String = "",
    firstName: String = "",
    lastName: String = "",
    picture: String = ""
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
            AsyncImage(
                model = picture,
                contentDescription = "Hello",
                onError = {
                    Timber.e("Load error")
                }
            )
            Text(email)
            Text(firstName)
            Text(lastName)
            Text(token)
        }
    }
}