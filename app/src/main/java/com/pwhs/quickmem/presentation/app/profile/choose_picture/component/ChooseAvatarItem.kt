package com.pwhs.quickmem.presentation.app.profile.choose_picture.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AvatarItem(
    avatarUrl: String = "",
    isSelected: Boolean = false,
    onSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .clickable { onSelected() }
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color.Blue else Color.Transparent
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Avatar Image",
            modifier = Modifier.fillMaxSize()
        )
    }
}
