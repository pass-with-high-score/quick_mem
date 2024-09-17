package com.pwhs.quickmem.presentation.app.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Composable
@Destination<RootGraph>
fun LibraryScreen(modifier: Modifier = Modifier) {
    Scaffold { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
        ) {
            Text("Library Screen")
        }
    }
}