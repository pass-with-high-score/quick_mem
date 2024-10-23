package com.pwhs.quickmem.presentation.app.study_set.study.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.sharp.Audiotrack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyFlipBottomSheet(
    modifier: Modifier = Modifier,
    showBottomSheet: Boolean = true,
    bottomSheetState: SheetState,
    onDismiss: () -> Unit = {},
    onShuffle: () -> Unit = {},
    onPlayAudio: () -> Unit = {},
    onSort: () -> Unit = {},
    onChangeCardOrientation: () -> Unit = {},
    onRestartFlashcards: () -> Unit = {}
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = bottomSheetState,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Options",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            onClick = onShuffle
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shuffle,
                                contentDescription = "Shuffle"
                            )
                        }
                        Text(
                            text = "Shuffle"
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            onClick = onPlayAudio
                        ) {
                            Icon(
                                imageVector = Icons.Sharp.Audiotrack,
                                contentDescription = "Audio"
                            )
                        }
                        Text(
                            text = "Play Audio",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sorting",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = true,
                        onCheckedChange = {}
                    )
                }
                Text(
                    text = "Card orientation",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = "Front",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Gray.copy(alpha = 0.5f)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Term"
                        )
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Definition"
                        )
                    }
                }
                TextButton(
                    onClick = onRestartFlashcards,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Restart flashcards",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun StudyFlipBottomSheetPreview() {
    val bottomSheetState = rememberModalBottomSheetState()
    MaterialTheme {
        StudyFlipBottomSheet(
            showBottomSheet = true,
            bottomSheetState = bottomSheetState
        )
    }
}