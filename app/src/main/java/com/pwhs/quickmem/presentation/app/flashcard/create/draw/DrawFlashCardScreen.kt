package com.pwhs.quickmem.presentation.app.flashcard.create.draw

import android.graphics.Bitmap
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.app.flashcard.create.draw.component.ControlsBar
import com.pwhs.quickmem.presentation.app.flashcard.create.draw.component.SeekbarBrushWidth
import com.pwhs.quickmem.util.convertToOldColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.rememberDrawController
import io.ak1.rangvikalp.RangVikalp
import io.ak1.rangvikalp.defaultSelectedColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@Destination<RootGraph>
@Composable
fun DrawFlashCardScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Bitmap>
) {

    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = bitmap.value) {
        Timber.d("Bitmap changed + ${bitmap.value}")
        bitmap.value?.let {
            scope.launch(Dispatchers.Main) {
                delay(1000)
                resultNavigator.setResult(it)
                navigator.navigateUp()
            }
        }
    }

    DrawFlashCard(
        modifier = modifier,
        onDrawFlashcard = {
            Timber.d("Bitmap drawn + $it")
            bitmap.value = it
        },
        onNavigateBack = {
            navigator.navigateUp()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawFlashCard(
    modifier: Modifier = Modifier,
    onDrawFlashcard: (Bitmap) -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    val undoVisibility = rememberSaveable { mutableStateOf(false) }
    val redoVisibility = rememberSaveable { mutableStateOf(false) }
    val colorBarVisibility = rememberSaveable { mutableStateOf(false) }
    val sizeBarVisibility = rememberSaveable { mutableStateOf(false) }
    val currentColor = remember { mutableStateOf(defaultSelectedColor) }
    val bg = colorScheme.onPrimary
    val currentBgColor = remember { mutableStateOf(bg) }
    val currentSize = rememberSaveable { mutableFloatStateOf(10f) }
    val colorIsBg = rememberSaveable { mutableStateOf(false) }
    val drawController = rememberDrawController()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.txt_draw), style = typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.txt_cancel_and_back),
                        )
                    }
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                drawController.saveBitmap()
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Back",
                            modifier = Modifier
                                .padding(8.dp),
                            tint = colorScheme.primary
                        )
                        Text(
                            text = stringResource(R.string.txt_save),
                            style = typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.primary
                            ), modifier = Modifier.padding(end = 8.dp)
                        )
                    }

                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            ControlsBar(
                drawController = drawController,
                onColorClick = {
                    colorBarVisibility.value = when (colorBarVisibility.value) {
                        false -> true
                        colorIsBg.value -> true
                        else -> false
                    }
                    colorIsBg.value = false
                    sizeBarVisibility.value = false
                },
                onBgColorClick = {
                    colorBarVisibility.value = when (colorBarVisibility.value) {
                        false -> true
                        !colorIsBg.value -> true
                        else -> false
                    }
                    colorIsBg.value = true
                    sizeBarVisibility.value = false
                },
                onSizeClick = {
                    sizeBarVisibility.value = !sizeBarVisibility.value
                    colorBarVisibility.value = false
                },
                undoVisibility = undoVisibility,
                redoVisibility = redoVisibility,
                colorValue = currentColor,
                bgColorValue = currentBgColor,
            )
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(400.dp)
                    .border(width = 1.dp, color = Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                DrawBox(
                    drawController = drawController,
                    backgroundColor = currentBgColor.value,
                    modifier = Modifier
                        .fillMaxSize()
                        .clipToBounds(),
                    bitmapCallback = { imageBitmap, error ->
                        imageBitmap?.let {
                            onDrawFlashcard(it.asAndroidBitmap())
                        }
                    }
                ) { undoCount, redoCount ->
                    sizeBarVisibility.value = false
                    colorBarVisibility.value = false
                    undoVisibility.value = undoCount != 0
                    redoVisibility.value = redoCount != 0
                }
            }

            RangVikalp(isVisible = colorBarVisibility.value, showShades = true) {
                if (colorIsBg.value) {
                    currentBgColor.value = it
                    drawController.changeBgColor(it)
                } else {
                    currentColor.value = it
                    drawController.changeColor(it)
                }
            }
            SeekbarBrushWidth(
                isVisible = sizeBarVisibility.value,
                progress = currentSize.floatValue.toInt(),
                progressColor = colorScheme.primary.convertToOldColor(),
                thumbColor = currentColor.value.convertToOldColor()
            ) {
                currentSize.floatValue = it.toFloat()
                drawController.changeStrokeWidth(it.toFloat())
            }
        }
    }

}