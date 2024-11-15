package com.pwhs.quickmem.presentation.app.profile.choose_picture.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R

@Composable
fun ChoosePictureList(
    modifier: Modifier = Modifier,
    avatarUrls: List<String> = emptyList(),
    selectedAvatarUrl: String?,
    onImageSelected: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 55.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "Camera",
                    modifier = Modifier
                        .size(80.dp)
                        .clickable {
                            // Action for Camera
                        },
                    tint = Color.Gray
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 55.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_gallecy),
                    contentDescription = "Camera",
                    modifier = Modifier
                        .size(80.dp)
                        .clickable {
                            // Action for Camera
                        },
                    tint = Color.Gray
                )
            }
        }

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

@Preview
@Composable
fun PreviewList() {
    ChoosePictureList(
        avatarUrls = emptyList(),
        selectedAvatarUrl = "",
        modifier = Modifier,
        onImageSelected = {}
    )
}
