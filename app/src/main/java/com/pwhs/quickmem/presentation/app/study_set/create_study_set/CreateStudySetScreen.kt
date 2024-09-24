package com.pwhs.quickmem.presentation.app.study_set.create_study_set

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.util.toColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun CreateStudySetScreen(modifier: Modifier = Modifier) {
    val sheetSubjectState = rememberModalBottomSheetState()
    rememberCoroutineScope()
    var showBottomSheetCreate by remember {
        mutableStateOf(false)
    }

    val defaultSubject = SubjectModel.defaultSubjects.first()
    Scaffold(
        containerColor = colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                title = {
                    Text(
                        text = "Create Flash Card",
                        style = typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSurface,
                            fontSize = 20.sp
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_done),
                            contentDescription = "Done",
                            tint = Color.Black
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Column {
                Text(
                    text = "Study Set name",
                    style = typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    placeholder = { Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = colorScheme.primary,
                        unfocusedIndicatorColor = colorScheme.onSurface.copy(alpha = 0.12f),
                    )
                )
            }
            Column(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    text = "Subject",
                    style = typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                OutlinedTextField(
                    value = defaultSubject.name,
                    onValueChange = { },
                    placeholder = { Text("Subject") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                        .clickable {
                            showBottomSheetCreate = true
                        },
                    readOnly = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = defaultSubject.iconRes!!),
                            contentDescription = defaultSubject.name,
                            modifier = Modifier.size(24.dp),
                            tint = Color.Black
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_down),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color.Black
                        )
                    },
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = colorScheme.primary,
                        unfocusedIndicatorColor = colorScheme.onSurface.copy(alpha = 0.12f),
                        disabledTextColor = Color.Black,
                        disabledContainerColor = Color.Transparent,
                        disabledIndicatorColor = colorScheme.onSurface.copy(alpha = 0.12f),
                        disabledPlaceholderColor = Color.Black,
                    ),
                )
            }

            Column(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    text = "Color",
                    style = typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                        .border(
                            width = 1.dp,
                            color = colorScheme.onSurface.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LazyRow(
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(vertical = 4.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(11.dp)
                    ) {
                        items(ColorModel.defaultColors) { color ->
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(
                                        color.hexValue
                                            .toColor()
                                            .copy(alpha = 0.5f)
                                    )
                                    .border(2.dp, Color.Transparent, CircleShape) // Add border
                                    .padding(4.dp), // Adjust padding
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(23.dp) // Adjust size to account for padding
                                        .clip(CircleShape)
                                        .background(color.hexValue.toColor()),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_done),
                                        contentDescription = "Selected",
                                        modifier = Modifier.size(16.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Make Study Set public",
                            style = typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Switch(
                            checked = true,
                            onCheckedChange = { }
                        )
                    }

                    Text(
                        text = "When you make a study set public, anyone can see it and use it.",
                        style = typography.bodySmall.copy(
                            color = colorScheme.onSurface.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

        }

        if (showBottomSheetCreate) {
            ModalBottomSheet(
                sheetState = sheetSubjectState,
                onDismissRequest = {
                    showBottomSheetCreate = false
                }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Subjects")
                    OutlinedTextField(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = "Search subject",
                                modifier = Modifier.size(24.dp),
                                tint = Color.Black
                            )
                        },
                        value = "",
                        onValueChange = { },
                        placeholder = { Text("Try Arts") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.onSurface.copy(alpha = 0.12f),
                            unfocusedContainerColor = colorScheme.onSurface.copy(alpha = 0.12f),
                            focusedIndicatorColor = colorScheme.primary,
                        )
                    )

                    LazyColumn {
                        items(SubjectModel.defaultSubjects) { subject ->
                            Row {
                                Icon(
                                    painter = painterResource(id = subject.iconRes!!),
                                    contentDescription = subject.name,
                                    tint = subject.color!!,
                                    modifier = Modifier.size(24.dp)
                                )

                                Text(
                                    text = subject.name,
                                    style = typography.bodyMedium.copy(
                                        color = colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CreateFlashCardScreenPreview() {
    CreateStudySetScreen()
}