package com.pwhs.quickmem.presentation.homescreen.components.data

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FolderCard(title: String, terms: String, author: String) {
    Surface(
        modifier = Modifier
            .width(220.dp)
            .height(110.dp)
            .clip(RoundedCornerShape(8.dp)),
        color = Color(0xFFF0F0F0),
        shadowElevation = 4.dp
    ) {

    }
}

@Preview
@Composable
fun FolderCardPV() {
}