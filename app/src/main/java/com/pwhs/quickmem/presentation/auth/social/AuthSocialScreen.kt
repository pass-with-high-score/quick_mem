package com.pwhs.quickmem.presentation.auth.social

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.annotation.parameters.DeepLink
import timber.log.Timber

@Destination<RootGraph>(
    deepLinks = [
        DeepLink(uriPattern = "quickmem://oauth/google/callback?token={token}&email={email}&firstName={firstName}&lastName={lastName}&picture={picture}"),
        DeepLink(uriPattern = "quickmem://oauth/google/callback?token={token}&email={email}&firstName={firstName}&lastName={lastName}&picture={picture}"),
    ]
)
@Composable
fun AuthSocialScreen(
    modifier: Modifier = Modifier, email: String = "",
    token: String = "",
    firstName: String = "",
    lastName: String = "",
    picture: String = ""
) {
    Scaffold(
        modifier = modifier.gradientBackground()
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