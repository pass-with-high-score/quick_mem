package com.pwhs.quickmem.presentation.app.flashcard.create

import android.Manifest
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.pwhs.quickmem.util.bitmapToUri
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.DrawFlashCardScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import timber.log.Timber

@Destination<RootGraph>(
    navArgs = CreateFlashCardArgs::class
)
@Composable
fun CreateFlashCardScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateFlashCardViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    canvasResultBack: ResultRecipient<DrawFlashCardScreenDestination, Bitmap>
) {
    val uiState by viewModel.uiState.collectAsState()
    val drawFlashCardResult = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    canvasResultBack.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                Timber.d("Canceled")
            }

            is NavResult.Value -> {
                Timber.d("Value: ${result.value}")
                drawFlashCardResult.value = result.value
                viewModel.onEvent(
                    CreateFlashCardUiAction.ImagesChanged(
                        context.bitmapToUri(result.value)
                    )
                )
            }
        }
    }
    CreateFlashCard(
        modifier = modifier,
        onDrawFlashcard = {
            navigator.navigate(DrawFlashCardScreenDestination)
        },
        onQuestionChanged = { viewModel.onEvent(CreateFlashCardUiAction.QuestionChanged(it)) },
        onImageChanged = { viewModel.onEvent(CreateFlashCardUiAction.ImagesChanged(it)) },
        images = uiState.images,
        onImageRemoved = { viewModel.onEvent(CreateFlashCardUiAction.ImagesRemoved(it)) }
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun CreateFlashCard(
    modifier: Modifier = Modifier,
    onQuestionChanged: (String) -> Unit = {},
    onAnswerChanged: (String) -> Unit = {},
    onDrawFlashcard: () -> Unit = {},
    images: List<Uri> = emptyList(),
    onImageChanged: (Uri) -> Unit = {},
    onImageRemoved: (Uri) -> Unit = {}
) {
    val richTextState = rememberRichTextState()
    var showRichTextControls by remember { mutableStateOf(false) }
    val readImagePermission = rememberPermissionState(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    )
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Timber.d("Permission granted")
        } else {
            Timber.d("Permission denied")
        }
    }
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            photoUri = uri
        }
    LaunchedEffect(photoUri) {
        photoUri?.let {
            onImageChanged(it)
        }
    }
    LaunchedEffect(richTextState.annotatedString) {
        Timber.d("AnnotatedString: ${richTextState.annotatedString.text}")
        onQuestionChanged(richTextState.annotatedString.text)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Create Flashcard",
                        style = typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Save"
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.TopCenter,
        ) {
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                item {
                    RichTextEditor(
                        modifier = Modifier
                            .fillMaxWidth(),
                        richTextState = richTextState,
                        showRichTextControls = showRichTextControls,
                        onShowRichTextControlsChanged = { showRichTextControls = it },
                        images = images,
                        onImageRemoved = onImageRemoved,
                        title = "Question",
                        placeholder = "Enter question"
                    )
                }
                item {
                    RichTextEditor(
                        modifier = Modifier
                            .fillMaxWidth(),
                        richTextState = richTextState,
                        showRichTextControls = showRichTextControls,
                        onShowRichTextControlsChanged = { showRichTextControls = it },
                        images = images,
                        onImageRemoved = onImageRemoved,
                        title = "Answer",
                        placeholder = "Enter answer"
                    )
                }
            }
            if (showRichTextControls) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .align(Alignment.BottomCenter)
                        .imePadding(),
                ) {
                    EditorControls(
                        onBoldClick = {
                            richTextState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        },
                        onItalicClick = {
                            richTextState.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                        },
                        onUnderlineClick = {
                            richTextState.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                        },
                        onTextColorClick = {
                            richTextState.toggleSpanStyle(SpanStyle(color = Color.Red))
                        },
                        onUnorderedListClick = {
                            richTextState.toggleUnorderedList()
                        },
                        onImageClick = {
                            if (!readImagePermission.status.isGranted) {
                                launcher.launch(
                                    PickVisualMediaRequest(
                                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            } else {
                                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        },
                        onDrawClick = onDrawFlashcard
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateFlashCardPreview() {
    CreateFlashCard()
}