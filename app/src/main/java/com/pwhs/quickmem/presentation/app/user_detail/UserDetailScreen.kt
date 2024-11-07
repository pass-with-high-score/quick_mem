@file:Suppress("UNUSED_EXPRESSION")

package com.pwhs.quickmem.presentation.app.user_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.app.library.classes.ListClassesScreen
import com.pwhs.quickmem.presentation.app.library.folder.ListFolderScreen
import com.pwhs.quickmem.presentation.app.library.study_set.ListStudySetScreen
import com.pwhs.quickmem.presentation.app.profile.ProfileViewModel
import com.pwhs.quickmem.presentation.app.user_detail.component.UserDetailTabEnum

@Destination<RootGraph>(navArgs = UserDetailArgs::class)
@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    studySets: List<GetStudySetResponseModel> = emptyList(),
    classes: List<GetClassByOwnerResponseModel> = emptyList(),
    folders: List<GetFolderResponseModel> = emptyList(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val (userName, avatarUrl) = profileViewModel.getUserData()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }

    UserDetail(
        modifier = modifier,
        isLoading = uiState.isLoading,
        userName = userName,
        avatarUrl = avatarUrl,
        studySets = studySets,
        classes = classes,
        folders = folders,
        onBackClick = { navigator.popBackStack() }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetail(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    userName: String = "",
    avatarUrl: String = "",
    studySets: List<GetStudySetResponseModel> = emptyList(),
    classes: List<GetClassByOwnerResponseModel> = emptyList(),
    folders: List<GetFolderResponseModel> = emptyList(),
    onBackClick: () -> Unit,
    onStudySetClick: (String) -> Unit = {},
) {
    var tabIndex by remember { mutableIntStateOf(UserDetailTabEnum.STUDY_SET.index) } // Default to STUDY_SET
    val tabTitles = listOf("Study set", "Class", "Folder")

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
                actions = {}
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
                    color = MaterialTheme.colorScheme.primary
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
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            TabRow(
                selectedTabIndex = tabIndex,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }

            when (tabIndex) {
                UserDetailTabEnum.STUDY_SET.index -> {
                    ListStudySetScreen(
                        studySets = studySets,
                        onStudySetClick = onStudySetClick
                    )
                }
                UserDetailTabEnum.CLASS.index -> {
                    ListClassesScreen(classes = classes)
                }
                UserDetailTabEnum.FOLDER.index -> {
                    ListFolderScreen(folders = folders)
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
