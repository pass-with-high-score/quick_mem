package com.pwhs.quickmem.presentation.auth.login.email

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination

@Composable
@Destination<RootGraph>
fun LoginWithEmailScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
        topBar = {
            AuthTopAppBar(
                onClick = {

                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) { }
    }
}