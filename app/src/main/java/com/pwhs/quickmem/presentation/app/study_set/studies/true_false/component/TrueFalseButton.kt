package com.pwhs.quickmem.presentation.app.study_set.studies.true_false.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.ui.theme.correctColor
import com.pwhs.quickmem.ui.theme.incorrectColor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TrueFalseButton(
    modifier: Modifier = Modifier,
    title: String = "",
    onClick: () -> Unit = {},
    isTrue: Boolean = false
) {
    val scope = rememberCoroutineScope()
    var debounceJob: Job? = null
    Card(
        modifier = modifier.padding(vertical = 8.dp),
        onClick = {
            debounceJob?.cancel()
            debounceJob = scope.launch {
                delay(500)
                onClick()
            }
        },
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = if (isTrue) Color(0xFFAAFF00) else Color(0xFFD31717),
        ),
        border = BorderStroke(
            width = 2.dp,
            color = if (isTrue) correctColor else incorrectColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Text(
                text = title, style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}

@Preview
@Composable
private fun TrueFalseButtonPreview() {
    QuickMemTheme {
        TrueFalseButton(title = "True", isTrue = true)
    }
}