package com.pwhs.quickmem.presentation.app.flashcard.create

import android.Manifest
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
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
    val context = LocalContext.current
    canvasResultBack.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                Timber.d("Canceled")
            }

            is NavResult.Value -> {
                viewModel.onEvent(
                    CreateFlashCardUiAction.AnswerImageChanged(context.bitmapToUri(result.value))
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
        answerImages = uiState.answerImage,
    )
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class,
)
@Composable
fun CreateFlashCard(
    modifier: Modifier = Modifier,
    question: String = "",
    onQuestionChanged: (String) -> Unit = {},
    answer: String = "",
    onAnswerChanged: (String) -> Unit = {},
    onDrawFlashcard: (Int) -> Unit = {},  // Add index for independent control
    answerImages: Uri? = null,
    onAnswerImageChanged: (Uri) -> Unit = {},
    onAnswerImageRemoved: (Uri) -> Unit = {},
) {
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

    var answerPhotoUri: Uri? by remember { mutableStateOf(null) }
    val answerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            answerPhotoUri = uri
        }

    LaunchedEffect(answerPhotoUri) {
        answerPhotoUri?.let {
            onAnswerImageChanged(it)
        }
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
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Done, contentDescription = "Save")
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(contentAlignment = Alignment.TopCenter) {
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            BasicTextField(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                value = question,
                                onValueChange = onQuestionChanged,
                                decorationBox = { innerTextField ->
                                    innerTextField()
                                }
                            )
                        }
                    )
                }
                item {
                    BasicTextField(
                        value = answer,
                        onValueChange = onAnswerChanged,
                        decorationBox = { innerTextField ->
                            innerTextField()
                        }
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