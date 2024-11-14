package com.pwhs.quickmem.presentation.app.profile.choose_picture.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import timber.log.Timber

@Composable
fun ChoosePictureList(
    modifier: Modifier = Modifier,
    avatarUrls: List<String>,
    selectedAvatarUrl: String?,
    onImageSelected: (String) -> Unit
) {
    Timber.tag("ChoosePictureList").d("Avatar URLs in List: $avatarUrls")

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(avatarUrls) { avatarUrl ->
            Timber.tag("AvatarItem").d("Avatar URL: $avatarUrl")

            AvatarItem(
                avatarUrl = avatarUrl,
                isSelected = avatarUrl == selectedAvatarUrl,
                onSelected = {
                    Timber.tag("AvatarItem").d("Selected Avatar: $avatarUrl")
                    onImageSelected(avatarUrl)
                }
            )
        }
    }
}