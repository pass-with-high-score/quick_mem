package com.pwhs.quickmem.presentation.app.profile.choose_picture

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.profile.choose_picture.component.ChoosePictureList
import com.pwhs.quickmem.presentation.app.profile.choose_picture.component.ChoosePictureTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination<RootGraph>
@Composable
fun ChoosePictureScreen(
    modifier: Modifier = Modifier,
    viewModel: ChoosePictureViewModel = hiltViewModel(),
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ChoosePictureUiEvent.AvatarUpdated -> {
                    Toast.makeText(context, "Update Image Successfully!", Toast.LENGTH_SHORT).show()
                    resultNavigator.navigateBack(true)
                }

                is ChoosePictureUiEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

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
        },
        onNavigateBack = {
            resultNavigator.navigateBack(false)
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
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ChoosePictureList(
                avatarUrls = avatarUrls,
                selectedAvatarUrl = selectedAvatarUrl,
                onImageSelected = onSelectedPicture
            )
        }
        LoadingOverlay(
            isLoading = isLoading
        )
    }
}


