package com.pwhs.quickmem.presentation.app.study_set.detail.progress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProgressTabScreen(modifier: Modifier = Modifier) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Progress Tab Screen")
        }
    }
}