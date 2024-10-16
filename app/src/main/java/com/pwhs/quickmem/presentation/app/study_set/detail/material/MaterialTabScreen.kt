package com.pwhs.quickmem.presentation.app.study_set.detail.material

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.flashcard.StudySetFlashCardResponseModel
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialTabScreen(
    modifier: Modifier = Modifier,
    flashCards: List<StudySetFlashCardResponseModel> = emptyList()
) {
    val listState = rememberLazyListState()
    val totalIndicators = 5
    var currentVisibleIndex by remember { mutableIntStateOf(0) }
    var menuBottomSheetState = rememberModalBottomSheetState()
    var showMenu by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                currentVisibleIndex = index % totalIndicators
            }
    }
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
                                fontWeight = Bold,
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
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        item {
                            LazyRow(
                                state = listState,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                items(flashCards) { flashCard ->
                                    FlipCard(
                                        frontText = flashCard.term,
                                        backText = flashCard.definition,
                                        backgroundColor = colorScheme.background,
                                        backImage = flashCard.definitionImageURL,
                                    )
                                }
                            }
                        }
                        item {
                            Text(
                                "Choose your way to study",
                                style = typography.titleMedium.copy(
                                    color = colorScheme.onSurface,
                                    fontWeight = Bold
                                ),
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        item {
                            LearnModeCard(
                                title = "Flip Flashcards",
                                icon = R.drawable.ic_flipcard,
                                onClick = {}
                            )
                        }
                        item {
                            LearnModeCard(
                                title = "Learn",
                                icon = R.drawable.ic_learn,
                                onClick = {}
                            )
                        }
                        item {
                            LearnModeCard(
                                title = "Test",
                                icon = R.drawable.ic_test,
                                onClick = {}
                            )
                        }
                        item {
                            LearnModeCard(
                                title = "Match",
                                icon = R.drawable.ic_match_card,
                                onClick = {}
                            )
                        }

                        item {
                            Row(
                                verticalAlignment = CenterVertically,
                                horizontalArrangement = SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Terms",
                                    style = typography.titleMedium.copy(
                                        color = colorScheme.onSurface,
                                        fontWeight = Bold
                                    ),
                                )

                                Row(
                                    horizontalArrangement = spacedBy(5.dp),
                                    verticalAlignment = CenterVertically
                                ) {
                                    Text(
                                        text = "Original"
                                    )
                                    Icon(
                                        imageVector = Filled.Sort,
                                        contentDescription = "Sort",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }

                        items(flashCards) { flashCards ->
                            CardDetail(
                                front = flashCards.term,
                                back = flashCards.definition,
                                isStarred = flashCards.isStarred,
                                imageURL = flashCards.definitionImageURL,
                                onStarClick = {},
                                onMenuClick = {
                                    showMenu = true
                                }
                            )
                        }

                    }
                }
            }
        }

        if (showMenu) {
            ModalBottomSheet(
                sheetState = menuBottomSheetState,
                onDismissRequest = {
                    showMenu = false
                }
            ) {
                Column {
                    Text("Edit")
                    Text("Delete")
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MaterialTabScreenPreview() {
    MaterialTabScreen(
        flashCards = listOf(
            StudySetFlashCardResponseModel(
                id = "1",
                term = "Term 1",
                definition = "Definition 1",
                definitionImageURL = "https://www.example.com/image1.jpg"
            ),
            StudySetFlashCardResponseModel(
                id = "2",
                term = "Term 2",
                definition = "Definition 2",
                definitionImageURL = "https://www.example.com/image2.jpg"
            ),
            StudySetFlashCardResponseModel(
                id = "3",
                term = "Term 3",
                definition = "Definition 3",
                definitionImageURL = "https://www.example.com/image3.jpg"
            ),
            StudySetFlashCardResponseModel(
                id = "4",
                term = "Term 4",
                definition = "Definition 4",
                definitionImageURL = "https://www.example.com/image4.jpg"
            ),
            StudySetFlashCardResponseModel(
                id = "5",
                term = "Term 5",
                definition = "Definition 5",
                definitionImageURL = "https://www.example.com/image5.jpg"
            ),
        )
    )
}