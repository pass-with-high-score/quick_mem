package com.pwhs.quickmem.presentation.app.library.classes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ListClassesScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("List Classes Screen")
        }
    }

}