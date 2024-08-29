package com.pwhs.quickmem.presentation.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import timber.log.Timber

@Composable
@Destination<RootGraph>(start = true)
fun LoginScreen(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    Timber.d("LoginScreen")
    Column {
        TextField(
            onValueChange = {
                email = it
            },
            value = email,
            label = {
                Text("Email")
            },
        )
    }
}