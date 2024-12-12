package com.pwhs.quickmem.presentation.app.flashcard.edit

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mr0xf00.easycrop.CropError
import com.mr0xf00.easycrop.CropResult
import com.mr0xf00.easycrop.CropperStyle
import com.mr0xf00.easycrop.crop
import com.mr0xf00.easycrop.rememberImageCropper
import com.mr0xf00.easycrop.rememberImagePicker
import com.mr0xf00.easycrop.ui.ImageCropperDialog
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.presentation.app.flashcard.component.CardSelectImage
import com.pwhs.quickmem.presentation.app.flashcard.component.FlashCardTextField
import com.pwhs.quickmem.presentation.app.flashcard.component.FlashCardTextFieldContainer
import com.pwhs.quickmem.presentation.app.flashcard.component.FlashCardTopAppBar
import com.pwhs.quickmem.presentation.app.flashcard.component.FlashcardBottomSheet
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.ImageCompressor
import com.pwhs.quickmem.util.bitmapToUri
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

@Destination<RootGraph>(
    navArgs = EditFlashCardArgs::class
)
@Composable
fun EditFlashCardScreen(
    modifier: Modifier = Modifier,
    viewModel: EditFlashCardViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Boolean>,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val imageCompressor = remember { ImageCompressor(context) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                EditFlashCardUiEvent.FlashCardSaved -> {
                    Timber.d("Flashcard saved")
                    Toast.makeText(
                        context,
                        context.getString(R.string.txt_flashcard_saved), Toast.LENGTH_SHORT
                    ).show()
                    resultNavigator.setResult(true)
                    navigator.navigateUp()
                }

                EditFlashCardUiEvent.FlashCardSaveError -> {
                    Timber.d("Flashcard save error")
                    Toast.makeText(
                        context,
                        context.getString(R.string.txt_flashcard_save_error), Toast.LENGTH_SHORT
                    ).show()
                }

                EditFlashCardUiEvent.LoadImage -> {
                    Timber.d("Load image")
                }
            }
        }
    }
    CreateFlashCard(
        modifier = modifier,
        term = uiState.term,
        definition = uiState.definition,
        definitionImageUri = uiState.definitionImageUri,
        definitionImageURL = uiState.definitionImageURL ?: "",
        hint = uiState.hint ?: "",
        showHint = uiState.showHint,
        explanation = uiState.explanation ?: "",
        showExplanation = uiState.showExplanation,
        isLoading = uiState.isLoading,
        onTermChanged = { viewModel.onEvent(EditFlashCardUiAction.FlashCardTermChanged(it)) },
        onDefinitionChanged = {
            viewModel.onEvent(
                EditFlashCardUiAction.FlashCardDefinitionChanged(
                    it
                )
            )
        },
        onDefinitionImageChanged = { uri ->
            if (uri == null) {
                viewModel.onEvent(EditFlashCardUiAction.FlashCardDefinitionImageChanged(null))
                return@CreateFlashCard
            }
            scope.launch {
                val compressedImageBytes = imageCompressor.compressImage(uri, 200 * 1024L) // 200KB
                val compressedImageUri = compressedImageBytes?.let {
                    Uri.fromFile(File(context.cacheDir, "compressed_image.jpg").apply {
                        writeBytes(it)
                    })
                }
                viewModel.onEvent(
                    EditFlashCardUiAction.FlashCardDefinitionImageChanged(
                        compressedImageUri
                    )
                )
            }
        },
        onHintChanged = { viewModel.onEvent(EditFlashCardUiAction.FlashCardHintChanged(it)) },
        onShowHintClicked = { viewModel.onEvent(EditFlashCardUiAction.ShowHintClicked(it)) },
        onExplanationChanged = {
            viewModel.onEvent(
                EditFlashCardUiAction.FlashCardExplanationChanged(
                    it
                )
            )
        },
        onShowExplanationClicked = {
            viewModel.onEvent(
                EditFlashCardUiAction.ShowExplanationClicked(
                    it
                )
            )
        },
        onUploadImage = { viewModel.onEvent(EditFlashCardUiAction.UploadImage(it)) },
        onDeleteImage = {
            viewModel.onEvent(
                EditFlashCardUiAction.RemoveImage(
                    uiState.definitionImageURL ?: ""
                )
            )
        },
        onNavigationBack = {
            resultNavigator.setResult(true)
            navigator.navigateUp()
        },
        onSaveFlashCardClicked = {
            viewModel.onEvent(EditFlashCardUiAction.SaveFlashCard)
        }
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun CreateFlashCard(
    modifier: Modifier = Modifier,
    term: String = "",
    definition: String = "",
    definitionImageUri: Uri? = null,
    definitionImageURL: String = "",
    isLoading: Boolean = false,
    hint: String = "",
    showHint: Boolean = false,
    explanation: String = "",
    showExplanation: Boolean = false,
    onTermChanged: (String) -> Unit = {},
    onDefinitionChanged: (String) -> Unit = {},
    onDefinitionImageChanged: (Uri?) -> Unit = {},
    onHintChanged: (String) -> Unit = {},
    onShowHintClicked: (Boolean) -> Unit = {},
    onExplanationChanged: (String) -> Unit = {},
    onShowExplanationClicked: (Boolean) -> Unit = {},
    onUploadImage: (Uri) -> Unit = {},
    onDeleteImage: () -> Unit = {},
    onNavigationBack: () -> Unit = {},
    onSaveFlashCardClicked: () -> Unit = {},
) {

    val bottomSheetSetting = rememberModalBottomSheetState()
    var showBottomSheetSetting by remember {
        mutableStateOf(false)
    }

    val imageCropper = rememberImageCropper()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val imagePicker = rememberImagePicker(onImage = { uri ->
        scope.launch {
            when (val result = imageCropper.crop(uri, context)) {
                CropResult.Cancelled -> { /* Handle cancellation */
                }

                is CropError -> { /* Handle error */
                }

                is CropResult.Success -> {
                    Timber.d("Cropped image: ${result.bitmap}")
                    onDefinitionImageChanged(context.bitmapToUri(result.bitmap))
                }
            }
        }
    })

    val cropState = imageCropper.cropState
    if (cropState != null) {
        ImageCropperDialog(
            state = cropState, style = CropperStyle(
                backgroundColor = Color.Black.copy(alpha = 0.8f),
                rectColor = Color.White,
                overlay = Color.Black.copy(alpha = 0.5f),
            )
        )
    }


    Scaffold(
        topBar = {
            FlashCardTopAppBar(
                onNavigationBack = onNavigationBack,
                onSaveFlashCardClicked = onSaveFlashCardClicked,
                enableSaveButton = term.isNotEmpty() && definition.isNotEmpty(),
                onSettingsClicked = {
                    showBottomSheetSetting = true
                },
                title = stringResource(R.string.txt_edit_flashcard)
            )
        },
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(contentAlignment = Alignment.TopCenter) {
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                item {
                    CardSelectImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        onUploadImage = onUploadImage,
                        definitionImageUri = definitionImageUri,
                        imagePicker = imagePicker,
                        definitionImageUrl = definitionImageURL,
                        onDeleteImage = onDeleteImage
                    )
                }
                item {
                    FlashCardTextFieldContainer(
                        term = term,
                        onTermChanged = onTermChanged,
                        definition = definition,
                        onDefinitionChanged = onDefinitionChanged
                    )
                }

                item {
                    if (showHint || hint.isNotEmpty()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            elevation = CardDefaults.elevatedCardElevation(
                                defaultElevation = 5.dp,
                                focusedElevation = 8.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = colorScheme.surface
                            ),
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    FlashCardTextField(
                                        value = hint,
                                        onValueChange = onHintChanged,
                                        hint = stringResource(R.string.txt_hint)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        onShowHintClicked(false)
                                        onHintChanged("")
                                    },
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = "Close",
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    if (showExplanation || explanation.isNotEmpty()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            elevation = CardDefaults.elevatedCardElevation(
                                defaultElevation = 5.dp,
                                focusedElevation = 8.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = colorScheme.surface
                            ),
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    FlashCardTextField(
                                        value = explanation,
                                        onValueChange = onExplanationChanged,
                                        hint = stringResource(R.string.txt_explanation)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        onShowExplanationClicked(false)
                                        onExplanationChanged("")
                                    },
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = "Close",
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.txt_make_your_term_and_definition_as_clear_as_possible_you_can_add_hint_and_explanation_to_help_you_remember_better),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .padding(bottom = 32.dp),
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }

            }
            BannerAds(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
            LoadingOverlay(isLoading = isLoading)
        }

        if (showBottomSheetSetting) {
            FlashcardBottomSheet(
                onDismissRequest = {
                    showBottomSheetSetting = false
                },
                sheetState = bottomSheetSetting,
                onShowHintClicked = onShowHintClicked,
                onShowExplanationClicked = onShowExplanationClicked
            )
        }
    }
}


@PreviewLightDark
@Composable
fun CreateFlashCardPreview() {
    QuickMemTheme {
        CreateFlashCard()
    }
}