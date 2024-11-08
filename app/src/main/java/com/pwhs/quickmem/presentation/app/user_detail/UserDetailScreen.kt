package com.pwhs.quickmem.presentation.app.user_detail

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.app.library.classes.ListClassesScreen
import com.pwhs.quickmem.presentation.app.library.folder.ListFolderScreen
import com.pwhs.quickmem.presentation.app.library.study_set.ListStudySetScreen
import com.pwhs.quickmem.presentation.app.user_detail.component.UserDetailTabEnum
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ClassDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FolderDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import timber.log.Timber

@Destination<RootGraph>(navArgs = UserDetailArgs::class)
@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultStudySetDetail: ResultRecipient<StudySetDetailScreenDestination, Boolean>,
    resultClassDetail: ResultRecipient<ClassDetailScreenDestination, Boolean>,
    resultFolderDetail: ResultRecipient<FolderDetailScreenDestination, Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    resultStudySetDetail.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                Timber.d("StudySetDetailScreen was canceled")
            }

            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(UserDetailUiAction.Refresh)
                }
            }
        }
    }

    resultClassDetail.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                Timber.d("ClassDetailScreen was canceled")
            }

            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(UserDetailUiAction.Refresh)
                }
            }
        }
    }

    resultFolderDetail.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                Timber.d("FolderDetailScreen was canceled")
            }

            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(UserDetailUiAction.Refresh)
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UserDetailUiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    UserDetail(
        modifier = modifier,
        isLoading = uiState.isLoading,
        isOwner = uiState.isOwner,
        userName = uiState.userName,
        avatarUrl = uiState.avatarUrl,
        studySets = uiState.studySets,
        classes = uiState.classes,
        folders = uiState.folders,
        onBackClick = { navigator.popBackStack() },
        onClassClick = {
            navigator.navigate(
                ClassDetailScreenDestination(
                    id = it.id,
                    description = it.description,
                    title = it.title,
                    code = ""
                )
            )
        },
        onStudySetClick = {
            navigator.navigate(
                StudySetDetailScreenDestination(
                    id = it.id,
                    code = it.linkShareCode ?: "",
                )
            )
        },
        onFolderClick = {
            navigator.navigate(
                FolderDetailScreenDestination(
                    id = it.id,
                    code = ""
                )
            )
        },
        onRefresh = {
            viewModel.onEvent(UserDetailUiAction.Refresh)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserDetail(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isOwner: Boolean = false,
    userName: String = "",
    avatarUrl: String = "",
    studySets: List<GetStudySetResponseModel> = emptyList(),
    classes: List<GetClassByOwnerResponseModel> = emptyList(),
    folders: List<GetFolderResponseModel> = emptyList(),
    onBackClick: () -> Unit,
    onStudySetClick: (GetStudySetResponseModel) -> Unit = {},
    onFolderClick: (GetFolderResponseModel) -> Unit = {},
    onClassClick: (GetClassByOwnerResponseModel) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var tabIndex by remember { mutableIntStateOf(UserDetailTabEnum.STUDY_SET.index) }
    val tabTitles = listOf("Study sets", "Classes", "Folders")

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    if (!isOwner) {
                        IconButton(
                            onClick = {
                                //TODO: Report user
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Report,
                                contentDescription = "Report user"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(50.dp),
                    color = colorScheme.primary
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatarUrl.ifEmpty { null })
                        .placeholder(R.drawable.default_avatar)
                        .error(R.drawable.default_avatar)
                        .build(),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = userName,
                    style = typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            TabRow(
                selectedTabIndex = tabIndex,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = colorScheme.primary
                    )
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                title, style = typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (tabIndex == index) Color.Black else Color.Gray
                                )
                            )
                        },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }

            when (tabIndex) {
                UserDetailTabEnum.STUDY_SET.index -> {
                    ListStudySetScreen(
                        studySets = studySets,
                        onStudySetClick = onStudySetClick,
                        onStudySetRefresh = onRefresh,
                        isLoading = isLoading,
                        isOwner = isOwner
                    )
                }

                UserDetailTabEnum.CLASS.index -> {
                    ListClassesScreen(
                        classes = classes,
                        isLoading = isLoading,
                        isOwner = isOwner,
                        onClassClicked = onClassClick,
                        onClassRefresh = onRefresh
                    )
                }

                UserDetailTabEnum.FOLDER.index -> {
                    ListFolderScreen(
                        folders = folders,
                        isLoading = isLoading,
                        isOwner = isOwner,
                        onFolderClick = onFolderClick,
                        onFolderRefresh = onRefresh
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserDetailPreview() {
    QuickMemTheme {
        UserDetail(
            userName = "John Doe",
            avatarUrl = "https://example.com/avatar.jpg",
            onBackClick = {}
        )
    }
}
