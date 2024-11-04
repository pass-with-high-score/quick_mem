package com.pwhs.quickmem.presentation.app.classes.detail.sets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.domain.model.flashcard.StudySetFlashCardResponseModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel

@Composable
fun SetsTabScreen(
    modifier: Modifier = Modifier,
    onFolderClicked: () -> Unit = {},
    flashCards: List<StudySetFlashCardResponseModel> = emptyList(),
    folder: List<GetFolderResponseModel> = emptyList(),
    onAddSetsClicked: () -> Unit = {},
    onFlashcardClicked: () -> Unit = {},

    ) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when {
                flashCards.isEmpty() && folder.isEmpty() -> {
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "This class has no sets",
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Add flashcard sets to share them with your class.",
                                color = Color.Gray,
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Button(
                                onClick = onAddSetsClicked,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(40.dp)
                            ) {
                                Text(
                                    text = "Add sets",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }

                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CardPReview() {
    SetsTabScreen()
}