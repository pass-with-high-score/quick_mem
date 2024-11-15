package com.pwhs.quickmem.presentation.app.profile.choose_picture.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AvatarItem(
    avatarUrl: String,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onSelected() }
            .border(
                width = if (isSelected) 4.dp else 2.dp,
                color = if (isSelected) Color(0xFF2BB3CD) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Avatar Image",
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxSize()
        )
    }
}


@Preview
@Composable
fun PreviewItem() {
    AvatarItem(
        avatarUrl = "https://api.quickmem.app/public/images/avatar/17.jpg",
        isSelected = true,
        onSelected = {}
    )
}

