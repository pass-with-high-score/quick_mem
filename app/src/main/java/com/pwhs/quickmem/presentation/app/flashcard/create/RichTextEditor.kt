package com.pwhs.quickmem.presentation.app.flashcard.create

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTextEditor(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String,
    showRichTextControls: Boolean,
    onShowRichTextControlsChanged: (Boolean) -> Unit,
    richTextState: RichTextState,
    images: List<Uri>,
    onImageRemoved: (Uri) -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            style = typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .onFocusChanged {
                    onShowRichTextControlsChanged(it.toString() == "ActiveParent")
                }
                .border(
                    color = if (showRichTextControls) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Gray
                    },
                    width = if (showRichTextControls) {
                        2.dp
                    } else {
                        1.dp
                    },
                    shape = shapes.medium
                )
        ) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                item {
                    com.mohamedrejeb.richeditor.ui.material3.RichTextEditor(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = richTextState,
                        placeholder = {
                            Text(
                                text = placeholder,
                                style = typography.bodyLarge.copy(
                                    color = Color.Gray
                                )
                            )
                        },
                        textStyle = typography.bodyLarge,
                        shape = RoundedCornerShape(10.dp),
                        colors = RichTextEditorDefaults.richTextEditorColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        )
                    )
                }
                item {
                    LazyRow {
                        items(images) { imageUri ->
                            Box {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        ImageRequest.Builder(LocalContext.current)
                                            .data(imageUri)
                                            .build()
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .width(100.dp)
                                        .height(100.dp)
                                        .border(
                                            1.dp,
                                            Color.Gray,
                                            RoundedCornerShape(10.dp)
                                        ),
                                    contentScale = ContentScale.Crop
                                )
                                IconButton(
                                    onClick = {
                                        onImageRemoved(imageUri)
                                    },
                                    modifier = Modifier.align(Alignment.TopEnd),
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = "Remove Image",
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurface
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