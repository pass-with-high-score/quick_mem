package com.pwhs.quickmem.presentation.app.home.component.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(50))
            .background(Color.White)
            .shadow(4.dp, RoundedCornerShape(50))
            .clickable { /* Handle click */ }
    ) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Row {
                    Icon(Icons.Default.Search, contentDescription = null)
                    Text(
                        text = "Flashcards, textbooks, question,...",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            },
            modifier = Modifier.fillMaxSize(),
            singleLine = true
        )
    }
}
