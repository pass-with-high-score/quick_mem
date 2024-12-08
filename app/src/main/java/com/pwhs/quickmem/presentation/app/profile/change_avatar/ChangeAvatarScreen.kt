package com.pwhs.quickmem.presentation.app.profile.change_avatar

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.app.profile.change_avatar.component.ChoosePictureList
import com.pwhs.quickmem.presentation.app.profile.change_avatar.component.ChangeAvatarTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination<RootGraph>
@Composable
fun ChangeAvatarScreen(
    modifier: Modifier = Modifier,
    viewModel: ChangeAvatarViewModel = hiltViewModel(),
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ChangeAvatarUiEvent.AvatarUpdated -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.txt_update_image_successfully),
                        Toast.LENGTH_SHORT
                    ).show()
                    resultNavigator.navigateBack(true)
                }

                is ChangeAvatarUiEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    ChangeAvatar(
        modifier = modifier,
        isLoading = uiState.isLoading,
        avatarUrls = uiState.avatarUrls,
        selectedAvatarUrl = uiState.selectedAvatarUrl,
        onSelectedPicture = { avatarUrl ->
            viewModel.onEvent(ChangeAvatarUiAction.ImageSelected(avatarUrl))
        },
        onDoneClick = {
            viewModel.onEvent(ChangeAvatarUiAction.SaveClicked)
        },
        onNavigateBack = {
            resultNavigator.navigateBack(false)
        }
    )
}

@Composable
fun ChangeAvatar(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    avatarUrls: List<String>,
    selectedAvatarUrl: String?,
    onNavigateBack: () -> Unit,
    onSelectedPicture: (String) -> Unit,
    onDoneClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ChangeAvatarTopAppBar(
                title = stringResource(R.string.txt_choose_a_picture),
                onDoneClick = onDoneClick,
                onNavigateBack = onNavigateBack
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            ChoosePictureList(
                avatarUrls = avatarUrls,
                selectedAvatarUrl = selectedAvatarUrl,
                onImageSelected = onSelectedPicture
            )
            LoadingOverlay(
                isLoading = isLoading
            )
        }
    }
}


