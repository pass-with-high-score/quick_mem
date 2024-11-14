package com.pwhs.quickmem.presentation.app.profile.choose_picture.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
            .clip(RoundedCornerShape(12.dp))  // Làm tròn góc
            .clickable { onSelected() }
            .background(if (isSelected) Color(0xFFE0F7FA) else Color.Transparent)  // Nền màu xanh nhạt khi được chọn
            .border(
                width = if (isSelected) 4.dp else 2.dp,
                color = if (isSelected) Color(0xFF00796B) else Color.LightGray,  // Viền xanh đậm khi được chọn
                shape = RoundedCornerShape(12.dp)
            )
            .shadow(if (isSelected) 8.dp else 2.dp, RoundedCornerShape(12.dp))  // Đổ bóng nổi bật khi được chọn
            .fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Avatar Image",
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))  // Làm tròn góc hình ảnh
                .fillMaxSize()
        )
    }
}

