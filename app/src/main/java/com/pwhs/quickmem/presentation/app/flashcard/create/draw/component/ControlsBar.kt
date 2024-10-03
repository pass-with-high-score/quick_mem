package com.pwhs.quickmem.presentation.app.flashcard.create.draw.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.FormatColorFill
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.ak1.drawbox.DrawController

@Composable
fun ControlsBar(
    modifier: Modifier = Modifier,
    drawController: DrawController,
    onColorClick: () -> Unit,
    onBgColorClick: () -> Unit,
    onSizeClick: () -> Unit,
    undoVisibility: MutableState<Boolean>,
    redoVisibility: MutableState<Boolean>,
    colorValue: MutableState<Color>,
    bgColorValue: MutableState<Color>,
) {
    Row(
        modifier = modifier.padding(12.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        MenuItems(
            icon = Icons.AutoMirrored.Filled.Undo,
            desc = "undo",
            colorTint = if (undoVisibility.value) colorScheme.onSurface else colorScheme.inversePrimary
        ) {
            if (undoVisibility.value) drawController.unDo()
        }
        MenuItems(
            icon = Icons.AutoMirrored.Filled.Redo,
            desc = "redo",
            colorTint = if (redoVisibility.value) colorScheme.onSurface else colorScheme.inversePrimary
        ) {
            if (redoVisibility.value) drawController.reDo()
        }
        MenuItems(
            icon = Icons.Default.Refresh,
            desc = "reset",
            colorTint = if (redoVisibility.value || undoVisibility.value) colorScheme.onSurface else colorScheme.inversePrimary
        ) {
            drawController.reset()
        }
        MenuItems(
            icon = Icons.Default.FormatColorFill,
            desc = "background color",
            colorTint = bgColorValue.value,
            border = bgColorValue.value == colorScheme.onPrimary,
        ) {
            onBgColorClick()
        }
        MenuItems(
            icon = Icons.Default.ColorLens,
            desc = "stroke color",
            colorTint = colorValue.value
        ) {
            onColorClick()
        }
        MenuItems(
            icon = Icons.Default.Brush,
            desc = "stroke size",
            colorTint = colorScheme.inversePrimary
        ) {
            onSizeClick()
        }
    }
}