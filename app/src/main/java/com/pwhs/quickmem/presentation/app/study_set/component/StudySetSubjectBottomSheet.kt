package com.pwhs.quickmem.presentation.app.study_set.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.subject.SubjectModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudySetSubjectBottomSheet(
    modifier: Modifier = Modifier,
    showBottomSheet: Boolean,
    sheetSubjectState: SheetState,
    searchSubjectQuery: String,
    onSearchQueryChange: (String) -> Unit,
    filteredSubjects: List<SubjectModel>,
    onSubjectSelected: (SubjectModel) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetSubjectState,
            onDismissRequest = onDismissRequest,
            containerColor = Color.White,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.padding(16.dp)

            ) {
                Text(
                    "Subjects",
                    style = typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                OutlinedTextField(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search subject",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Black
                        )
                    },
                    value = searchSubjectQuery,
                    shape = RoundedCornerShape(10.dp),
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Try Arts") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = colorScheme.primary,
                        unfocusedIndicatorColor = colorScheme.onSurface.copy(alpha = 0.12f),
                    )
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(filteredSubjects) { subject ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .clickable {
                                    onSubjectSelected(subject)
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(id = subject.iconRes!!),
                                contentDescription = subject.name,
                                tint = subject.color!!,
                                modifier = Modifier
                                    .size(24.dp)
                            )

                            Text(
                                text = subject.name,
                                style = typography.bodyMedium.copy(
                                    color = colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                                    .padding(start = 10.dp)

                            )
                        }
                    }
                    item {
                        if (filteredSubjects.isEmpty()) {
                            Text(
                                text = "No subjects found",
                                style = typography.bodyMedium.copy(
                                    color = colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}