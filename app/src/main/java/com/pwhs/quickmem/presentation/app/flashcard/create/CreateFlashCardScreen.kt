package com.pwhs.quickmem.presentation.app.flashcard.create

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
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
    canvasResultBack.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                Timber.d("Canceled")
            }

            is NavResult.Value -> {
                Timber.d("Value: ${result.value}")
                drawFlashCardResult.value = result.value
            }
        }
    }
    CreateFlashCard(
        modifier = modifier,
        onDrawFlashcard = {
            navigator.navigate(DrawFlashCardScreenDestination)
        },
        drawFlashCardResult = drawFlashCardResult.value,
        studySetTitle = uiState.studySetTitle
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFlashCard(
    modifier: Modifier = Modifier,
    studySetTitle: String = "",
    question: String = "",
    onQuestionChanged: (String) -> Unit = {},
    answer: String = "",
    onAnswerChanged: (String) -> Unit = {},
    onDrawFlashcard: () -> Unit = {},
    drawFlashCardResult: Bitmap? = null
) {
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
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            Column(
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Question")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .border(
                            color = Color.Gray,
                            width = 1.dp
                        )
                ) {
                    BasicTextField(
                        value = question,
                        onValueChange = { onQuestionChanged(it) },
                        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            if (question.isEmpty()) {
                                Text(text = "Add a question", color = Color.Gray)
                            }
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