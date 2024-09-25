package com.pwhs.quickmem.presentation.app.study_set.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Destination<RootGraph>
@Composable
fun StudySetDetailScreen(modifier: Modifier = Modifier) {
    Text(
        "Hello from StudySetDetailScreen!",
    )
}