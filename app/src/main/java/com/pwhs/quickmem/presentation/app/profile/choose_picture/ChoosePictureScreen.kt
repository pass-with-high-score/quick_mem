package com.pwhs.quickmem.presentation.app.profile.choose_picture

import android.widget.Toast
import com.pwhs.quickmem.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.profile.choose_picture.component.ChoosePictureList
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

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ChoosePictureUiEvent.AvatarUpdated -> {
                    Toast.makeText(context, "Avatar Updated: ${event.avatarUrl}", Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(ChoosePictureUiAction.ImageSelected(event.avatarUrl))
                }
                is ChoosePictureUiEvent.Error -> {

                }
            }
        }
    }

    Timber.tag("ChoosePictureScreen").d("Avatar URLs: ${uiState.avatarUrls}")

    ChoosePicture(
        modifier = modifier,
        isLoading = uiState.isLoading,
        avatarUrls = uiState.avatarUrls,
        selectedAvatarUrl = uiState.selectedAvatarUrl,
        onSelectedPicture = { avatarUrl ->
            viewModel.onEvent(ChoosePictureUiAction.ImageSelected(avatarUrl))
        },
        onDoneClick = {
            viewModel.onEvent(ChoosePictureUiAction.SaveClicked)
            navigator.navigateUp()
        },
        onNavigateBack = {
            navigator.navigateUp()
        }
    )
}

@Composable
fun ChoosePicture(
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
                        .clickable {

                        },
                    tint = Color.Gray
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_gallecy),
                    contentDescription = "Gallery",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {

                        },
                    tint = Color.Gray
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else {
                ChoosePictureList(
                    avatarUrls = avatarUrls,
                    selectedAvatarUrl = selectedAvatarUrl,
                    onImageSelected = onSelectedPicture
                )
            }
        }
    }
}


