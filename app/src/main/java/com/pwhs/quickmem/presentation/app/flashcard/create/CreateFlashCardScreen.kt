package com.pwhs.quickmem.presentation.app.flashcard.create

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.DrawFlashCardScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import timber.log.Timber

@Destination<RootGraph>
@Composable
fun CreateFlashCardScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    canvasResultBack: ResultRecipient<DrawFlashCardScreenDestination, Bitmap>
) {
    val drawFlashCardResult = remember { mutableStateOf<Bitmap?>(null) }
    canvasResultBack.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                Timber.d("Canceled")
            }

            is NavResult.Value -> {
                Timber.d("Value: ${result.value}")
                if (result.value != null) {
                    drawFlashCardResult.value = result.value
                }
            }
        }
    }
    CreateFlashCard(
        modifier = modifier,
        onDrawFlashcard = {
            navigator.navigate(DrawFlashCardScreenDestination)
        },
        drawFlashCardResult = drawFlashCardResult.value
    )
}

@Composable
fun CreateFlashCard(
    modifier: Modifier = Modifier,
    onDrawFlashcard: () -> Unit = {},
    drawFlashCardResult: Bitmap? = null
) {
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            drawFlashCardResult?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Drawn Flashcard",
                )
            }
            Button(
                onClick = onDrawFlashcard
            ) {
                Text("Draw Flashcard")
            }
        }
    }
}