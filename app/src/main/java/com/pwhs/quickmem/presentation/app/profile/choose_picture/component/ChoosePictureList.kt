package com.pwhs.quickmem.presentation.app.profile.choose_picture.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChoosePictureList(
    modifier: Modifier = Modifier,
    avatarUrls: List<String>,
    selectedAvatarUrl: String?,
    onImageSelected: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(avatarUrls) { avatarUrl ->
            AvatarItem(
                avatarUrl = avatarUrl,
                isSelected = avatarUrl == selectedAvatarUrl,
                onSelected = {
                    onImageSelected(avatarUrl)
                }
            )
        }
    }
}
