package com.pwhs.quickmem.presentation.app.study_set.detail.material

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pwhs.quickmem.domain.model.flashcard.StudySetFlashCardResponseModel
import timber.log.Timber

@Composable
fun MaterialTabScreen(
    modifier: Modifier = Modifier,
    flashCards: List<StudySetFlashCardResponseModel> = emptyList()
) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Center
        ) {
            when {
                flashCards.isEmpty() -> {
                    Column(
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Text(
                            "Add your material to get started",
                            style = typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.onSurface
                            ),
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "This Study Set can contain flashcards, notes, and files on certain topic",
                            textAlign = TextAlign.Center,
                            style = typography.bodyMedium.copy(
                                color = colorScheme.onSurface,
                            ),
                        )
                        Button(
                            onClick = {},
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = CenterVertically
                            ) {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = "Add",
                                    tint = colorScheme.background,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    "Add Material",
                                    style = typography.titleMedium.copy(
                                        color = colorScheme.background
                                    )
                                )
                            }
                        }
                    }
                }

                else -> {
                    LazyColumn {
                        items(flashCards.size) { flashCard ->
                            val item = flashCards[flashCard]
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = item.term,
                                        style = typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(16.dp)
                                    )
                                    Text(
                                        text = item.definition,
                                        style = typography.bodyMedium,
                                        modifier = Modifier.padding(16.dp)
                                    )

                                    Timber.d("Image URL: ${item.definitionImageURL}")
                                    item.definitionImageURL?.let {
                                        // Image
                                        AsyncImage(
                                            contentScale = ContentScale.Crop,
                                            model = it,
                                            contentDescription = "Image",
                                            modifier = Modifier.size(100.dp),
                                            onError = {
                                                Timber.e("Error loading image: $it")
                                            },
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MaterialTabScreenPreview() {
    MaterialTabScreen()
}