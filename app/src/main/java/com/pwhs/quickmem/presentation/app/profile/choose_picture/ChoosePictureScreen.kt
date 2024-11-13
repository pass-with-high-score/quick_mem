package com.pwhs.quickmem.presentation.app.profile.choose_picture

import com.pwhs.quickmem.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pwhs.quickmem.presentation.app.profile.choose_picture.component.ChoosePictureTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@Destination<RootGraph>
@Composable
fun ChoosePictureScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: ChoosePictureViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Timber.tag("ChoosePictureScreen").d("Avatar URLs: ${uiState.avatarUrls}")

    ChoosePictureUI(
        modifier = modifier,
        isLoading = uiState.isLoading,
        avatarUrls = uiState.avatarUrls,
        selectedAvatarUrl = uiState.selectedAvatarUrl,
        onSelectedPicture = {

        },
        onDoneClick = {
            navigator.navigateUp()
        },
        onNavigateBack = {
            navigator.navigateUp()
        }
    )
}

@Composable
fun ChoosePictureUI(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    avatarUrls: List<String>,
    selectedAvatarUrl: String?,
    onNavigateBack: () -> Unit,
    onSelectedPicture: (String) -> Unit,
    title: String = "Choose a picture",
    onDoneClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ChoosePictureTopAppBar(
                title = title,
                onDoneClick = onDoneClick,
                onNavigateBack = onNavigateBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "Camera",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { },
                    tint = Color.Gray
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_gallecy),
                    contentDescription = "Gallery",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { },
                    tint = Color.Gray
                )
            }

            ChoosePictureList(
                avatarUrls = avatarUrls,
                onImageSelected = onSelectedPicture
            )
        }
    }
}

@Composable
fun ChoosePictureList(
    modifier: Modifier = Modifier,
    avatarUrls: List<String>,
    onImageSelected: (String) -> Unit
) {
    Timber.tag("ChoosePictureList").d("Avatar URLs in List: $avatarUrls")


    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        items(avatarUrls) { avatarUrl ->
            Timber.tag("AvatarItem").d("Avatar URL: $avatarUrl")

            AvatarItem(
                avatarUrl = avatarUrl,
                onSelected = {
                    Timber.tag("AvatarItem").d("Selected Avatar: $avatarUrl")
                    onImageSelected(avatarUrl)
                }
            )
        }
    }
}


@Composable
fun AvatarItem(
    avatarUrl: String = "",
    onSelected: () -> Unit
) {
    AsyncImage(
        model = avatarUrl,
        contentDescription = "Avatar Image",
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable {
                onSelected()
            }
    )
}

