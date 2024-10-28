package com.pwhs.quickmem.presentation.app.study_set.edit

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.presentation.app.study_set.component.StudySetColorInput
import com.pwhs.quickmem.presentation.app.study_set.component.StudySetPublicSwitch
import com.pwhs.quickmem.presentation.app.study_set.component.StudySetSubjectBottomSheet
import com.pwhs.quickmem.presentation.app.study_set.component.StudySetSubjectInput
import com.pwhs.quickmem.presentation.app.study_set.component.StudySetTextField
import com.pwhs.quickmem.presentation.app.study_set.component.StudySetTopAppBar
import com.pwhs.quickmem.util.loadingOverlay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination<RootGraph>(
    navArgs = EditStudySetScreenArgs::class
)
@Composable
fun EditStudySetScreen(
    modifier: Modifier = Modifier,
    viewModel: EditStudySetViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Boolean>,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                EditStudySetUiEvent.None -> TODO()

                is EditStudySetUiEvent.ShowError -> {
                    showLoading = false
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                EditStudySetUiEvent.ShowLoading -> {
                    showLoading = true
                }

                is EditStudySetUiEvent.StudySetEdited -> {
                    showLoading = false
                    Toast.makeText(context, "Study Set Edited", Toast.LENGTH_SHORT).show()
                    resultNavigator.setResult(true)
                    navigator.popBackStack()
                }
            }
        }
    }

    EditStudySet(
        modifier = modifier.loadingOverlay(showLoading),
        title = uiState.title,
        titleError = uiState.titleError,
        onTitleChange = { viewModel.onEvent(EditStudySetUiAction.TitleChanged(it)) },
        description = uiState.description,
        onDescriptionChange = { viewModel.onEvent(EditStudySetUiAction.DescriptionChanged(it)) },
        subjectModel = uiState.subjectModel,
        onSubjectChange = { viewModel.onEvent(EditStudySetUiAction.SubjectChanged(it)) },
        colorModel = uiState.colorModel,
        onColorChange = { viewModel.onEvent(EditStudySetUiAction.ColorChanged(it)) },
        isPublic = uiState.isPublic,
        onIsPublicChange = { viewModel.onEvent(EditStudySetUiAction.PublicChanged(it)) },
        onDoneClick = { viewModel.onEvent(EditStudySetUiAction.SaveClicked) },
        onNavigateBack = { navigator.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStudySet(
    modifier: Modifier = Modifier,
    title: String = "",
    titleError: String = "",
    onTitleChange: (String) -> Unit = {},
    description: String = "",
    descriptionError: String = "",
    onDescriptionChange: (String) -> Unit = {},
    subjectModel: SubjectModel? = SubjectModel.defaultSubjects.first(),
    onSubjectChange: (SubjectModel) -> Unit = {},
    colorModel: ColorModel? = ColorModel.defaultColors.first(),
    onColorChange: (ColorModel) -> Unit = {},
    isPublic: Boolean = true,
    onIsPublicChange: (Boolean) -> Unit = {},
    onDoneClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val sheetSubjectState = rememberModalBottomSheetState()
    rememberCoroutineScope()
    var showBottomSheetEdit by remember {
        mutableStateOf(false)
    }
    var searchSubjectQuery by remember {
        mutableStateOf("")
    }
    val filteredSubjects = SubjectModel.defaultSubjects.filter {
        it.name.contains(searchSubjectQuery, ignoreCase = true)
    }

    Scaffold(
        containerColor = colorScheme.background,
        modifier = modifier,
        topBar = {
            StudySetTopAppBar(
                onNavigateBack = onNavigateBack,
                onDoneClick = onDoneClick,
                title = "Edit Study Set"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            StudySetTextField(
                value = title,
                title = "Study Set Title",
                valueError = titleError,
                onValueChange = onTitleChange,
                placeholder = "Enter Study Set Title"
            )
            StudySetTextField(
                value = description,
                valueError = descriptionError,
                onValueChange = onDescriptionChange,
                title = "Description (optional)",
                placeholder = "Enter Description"
            )
            StudySetSubjectInput(
                subjectModel = subjectModel,
                onShowBottomSheet = {
                    showBottomSheetEdit = true
                }
            )
            StudySetColorInput(
                colorModel = colorModel,
                onColorChange = onColorChange
            )
            StudySetPublicSwitch(
                isPublic = isPublic,
                onIsPublicChange = onIsPublicChange
            )

        }

        StudySetSubjectBottomSheet(
            showBottomSheet = showBottomSheetEdit,
            sheetSubjectState = sheetSubjectState,
            searchSubjectQuery = searchSubjectQuery,
            onSearchQueryChange = { searchSubjectQuery = it },
            filteredSubjects = filteredSubjects,
            onSubjectSelected = {
                onSubjectChange(it)
                showBottomSheetEdit = false
            },
            onDismissRequest = { showBottomSheetEdit = false }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun EditFlashCardScreenPreview() {
    EditStudySet()
}