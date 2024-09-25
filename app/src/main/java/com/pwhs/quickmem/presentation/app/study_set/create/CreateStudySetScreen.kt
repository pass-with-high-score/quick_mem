package com.pwhs.quickmem.presentation.app.study_set.create

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.color.ColorModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.util.loadingOverlay
import com.pwhs.quickmem.util.toColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun CreateStudySetScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateStudySetViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {

                CreateStudySetUiEvent.None -> TODO()
                CreateStudySetUiEvent.SaveClicked -> {
                    viewModel.onEvent(CreateStudySetUiAction.SaveClicked)
                }

                is CreateStudySetUiEvent.ShowError -> {
                    showLoading = false
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                CreateStudySetUiEvent.ShowLoading -> {
                    showLoading = true
                }

                is CreateStudySetUiEvent.StudySetCreated -> {
                    showLoading = false
                    Toast.makeText(context, "Study Set Created", Toast.LENGTH_SHORT).show()
                    navigator.navigateUp()
                    navigator.navigate(StudySetDetailScreenDestination(id = event.id))
                }
            }
        }
    }

    CreateStudySet(
        modifier = modifier.loadingOverlay(showLoading),
        title = uiState.title,
        titleError = uiState.titleError,
        onTitleChange = { viewModel.onEvent(CreateStudySetUiAction.NameChanged(it)) },
        subjectModel = uiState.subjectModel,
        onSubjectChange = { viewModel.onEvent(CreateStudySetUiAction.SubjectChanged(it)) },
        colorModel = uiState.colorModel,
        onColorChange = { viewModel.onEvent(CreateStudySetUiAction.ColorChanged(it)) },
        isPublic = uiState.isPublic,
        onIsPublicChange = { viewModel.onEvent(CreateStudySetUiAction.PublicChanged(it)) },
        onDoneClick = { viewModel.onEvent(CreateStudySetUiAction.SaveClicked) },
        onNavigateBack = { navigator.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateStudySet(
    modifier: Modifier = Modifier,
    title: String = "",
    titleError: String = "",
    onTitleChange: (String) -> Unit = {},
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
    var showBottomSheetCreate by remember {
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
                        onClick = onDoneClick,
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
                        onClick = onNavigateBack,
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
                    shape = RoundedCornerShape(10.dp),
                    value = title,
                    onValueChange = onTitleChange,
                    placeholder = { Text("Title") },
                    isError = titleError.isNotEmpty(),
                    supportingText = {
                        titleError.isNotEmpty().let {
                            Text(titleError)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = colorScheme.primary,
                        focusedSupportingTextColor = colorScheme.error,
                        unfocusedSupportingTextColor = colorScheme.error,
                        errorContainerColor = Color.Transparent,
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
                    shape = RoundedCornerShape(10.dp),
                    value = subjectModel!!.name,
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
                            painter = painterResource(id = subjectModel.iconRes!!),
                            contentDescription = subjectModel.name,
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
                            color = colorModel?.hexValue?.toColor() ?: colorScheme.onSurface.copy(
                                alpha = 0.12f
                            ),
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
                                        if (colorModel?.hexValue == color.hexValue) {
                                            color.hexValue
                                                .toColor()
                                                .copy(alpha = 0.5f)
                                        } else {
                                            Color.Transparent
                                        }
                                    )
                                    .border(2.dp, Color.Transparent, CircleShape)
                                    .padding(4.dp)
                                    .clickable {
                                        onColorChange(color)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(23.dp)
                                        .clip(CircleShape)
                                        .background(color.hexValue.toColor()),
                                    contentAlignment = Alignment.Center
                                ) {
                                    this@Row.AnimatedVisibility(
                                        visible = colorModel?.hexValue == color.hexValue,
                                        enter = fadeIn(),
                                        exit = fadeOut()
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
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surfaceContainerLow
                )
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
                            checked = isPublic,
                            onCheckedChange = onIsPublicChange,
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
                },
                containerColor = Color.White,
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
                                modifier = Modifier.size(20.dp),
                                tint = Color.Black
                            )
                        },
                        value = searchSubjectQuery,
                        shape = RoundedCornerShape(10.dp),
                        onValueChange = { searchSubjectQuery = it },
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
                                        onSubjectChange(subject)
                                        showBottomSheetCreate = false
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
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CreateFlashCardScreenPreview() {
    CreateStudySet()
}