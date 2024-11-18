package com.pwhs.quickmem.presentation.app.search_result.study_set.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.presentation.app.search_result.component.TopBarSearch
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultCreatorEnum
import com.pwhs.quickmem.presentation.app.search_result.study_set.enum.SearchResultSizeEnum
import com.pwhs.quickmem.presentation.app.study_set.component.StudySetColorInput
import com.pwhs.quickmem.presentation.app.study_set.component.StudySetSubjectBottomSheet
import com.pwhs.quickmem.presentation.app.study_set.component.StudySetSubjectInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterStudySetBottomSheet(
    modifier: Modifier = Modifier,
    colorModel: ColorModel? = ColorModel.defaultColors.first(),
    onColorChange: (ColorModel) -> Unit = {},
    onSubjectChange: (SubjectModel) -> Unit = {},
    subjectModel: SubjectModel? = SubjectModel.defaultSubjects.first(),
    sizeModel: SearchResultSizeEnum = SearchResultSizeEnum.ALL,
    onSizeChange: (SearchResultSizeEnum) -> Unit,
    isAiGenerated: Boolean = false,
    onIsAiGeneratedChange: (Boolean) -> Unit,
    creatorTypeModel: SearchResultCreatorEnum = SearchResultCreatorEnum.ALL,
    onCreatorChange: (SearchResultCreatorEnum) -> Unit,
    onNavigateBack: () -> Unit,
    onResetClick: () -> Unit,
    onApplyClick: () -> Unit
) {
    val sheetSubjectState = rememberModalBottomSheetState()
    var showBottomSheetCreate by remember { mutableStateOf(false) }
    var searchSubjectQuery by remember { mutableStateOf("") }

    val filteredSubjects = SubjectModel.defaultSubjects.filter {
        it.name.contains(searchSubjectQuery, ignoreCase = true)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding()
            .padding(16.dp)
    ) {
        item {
            TopBarSearch(
                title = "Filters",
                onNavigateBack = onNavigateBack,
                onResetClick = onResetClick
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            FilterSection(
                title = "Number of terms",
                options = SearchResultSizeEnum.entries.map { it.title },
                selectedOption = sizeModel.title,
                onOptionSelected = { selectedContent ->
                    val selectedSize =
                        SearchResultSizeEnum.entries.first { it.title == selectedContent }
                    onSizeChange(selectedSize)
                }
            )
        }

        item {
            FilterSection(
                title = "Created by",
                options = SearchResultCreatorEnum.entries.map { it.title },
                selectedOption = creatorTypeModel.title,
                onOptionSelected = {
                    val selectedCreator =
                        SearchResultCreatorEnum.entries.first { entry -> entry.title == it }
                    onCreatorChange(selectedCreator)
                }
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "AI Generated",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = isAiGenerated,
                    onCheckedChange = { onIsAiGeneratedChange(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF4752A6),
                        uncheckedThumbColor = Color.Gray
                    )
                )
            }
        }

        item {
            StudySetSubjectInput(
                subjectModel = subjectModel,
                onShowBottomSheet = {
                    showBottomSheetCreate = true
                }
            )
        }
        item {
            StudySetColorInput(
                colorModel = colorModel,
                onColorChange = onColorChange
            )
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        item {
            Button(
                onClick = onApplyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4752A6))
            ) {
                Text(
                    "Apply",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    StudySetSubjectBottomSheet(
        showBottomSheet = showBottomSheetCreate,
        sheetSubjectState = sheetSubjectState,
        searchSubjectQuery = searchSubjectQuery,
        onSearchQueryChange = { searchSubjectQuery = it },
        filteredSubjects = filteredSubjects,
        onSubjectSelected = {
            onSubjectChange(it)
            showBottomSheetCreate = false
        },
        onDismissRequest = { showBottomSheetCreate = false }
    )

}
