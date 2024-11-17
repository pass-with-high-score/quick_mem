package com.pwhs.quickmem.presentation.app.search_result.all_result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.users.SearchUserResponseModel
import com.pwhs.quickmem.domain.model.users.UserResponseModel
import com.pwhs.quickmem.presentation.app.library.classes.component.ClassItem
import com.pwhs.quickmem.presentation.app.library.folder.component.FolderItem
import com.pwhs.quickmem.presentation.app.library.study_set.component.StudySetItem
import com.pwhs.quickmem.presentation.app.search_result.all_result.component.SectionHeader
import com.pwhs.quickmem.presentation.app.search_result.user.component.SearchUserResultItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAllResultScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    studySets: LazyPagingItems<GetStudySetResponseModel>? = null,
    onStudySetClick: (GetStudySetResponseModel?) -> Unit = {},
    folders: LazyPagingItems<GetFolderResponseModel>? = null,
    onFolderClick: (GetFolderResponseModel?) -> Unit = {},
    classes: List<GetClassByOwnerResponseModel> = emptyList(),
    onClassClicked: (GetClassByOwnerResponseModel) -> Unit = {},
    users: List<SearchUserResponseModel> = emptyList(),
    onMembersItemClicked: (SearchUserResponseModel) -> Unit = {},
    onSearchResultRefresh: () -> Unit = {},
    onSeeAllClickStudySet: () -> Unit = {},
    onSeeAllClickFolder: () -> Unit = {},
    onSeeAllClickClass: () -> Unit = {},
    onSeeAllClickUsers: () -> Unit = {}
) {
    val refreshState = rememberPullToRefreshState()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = modifier.fillMaxSize(),
            state = refreshState,
            isRefreshing = isLoading,
            onRefresh = {
                onSearchResultRefresh()
            }
        ) {
            when (studySets?.itemCount == 0 && folders?.itemCount == 0 && classes.isEmpty() && users.isEmpty()) {
                true -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(top = 40.dp)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_flashcards),
                            contentDescription = "No results found",
                        )
                        Text(
                            text = "No results found",
                            style = typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                false -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                    ) {
                        if (studySets?.itemCount != 0) {
                            item {
                                SectionHeader(
                                    title = "Study Sets",
                                    onSeeAllClick = onSeeAllClickStudySet
                                )
                            }
                            items(studySets?.itemCount?.coerceAtMost(4) ?: 0) {
                                val studySet = studySets?.get(it)
                                StudySetItem(
                                    studySet = studySet,
                                    onStudySetClick = { onStudySetClick(studySet) }
                                )
                            }
                        }

                        if (folders?.itemCount != 0) {
                            item {
                                SectionHeader(
                                    title = "Folders",
                                    onSeeAllClick = onSeeAllClickFolder
                                )
                            }
                            items(folders?.itemCount?.coerceAtMost(4) ?: 0) {
                                val folder = folders?.get(it)
                                FolderItem(
                                    title = folder?.title ?: "",
                                    numOfStudySets = folder?.studySetCount ?: 0,
                                    onClick = { onFolderClick(folder) },
                                    userResponseModel = folder?.owner ?: UserResponseModel()
                                )
                            }
                        }

                        if (classes.isNotEmpty()) {
                            item {
                                SectionHeader(
                                    title = "Classes",
                                    onSeeAllClick = onSeeAllClickClass
                                )
                            }
                            items(classes.take(4)) { classItem ->
                                ClassItem(
                                    classItem = classItem,
                                    onClick = { onClassClicked(classItem) }
                                )
                            }
                        }

                        if (users.isNotEmpty()) {
                            item {
                                SectionHeader(
                                    title = "Users",
                                    onSeeAllClick = onSeeAllClickUsers
                                )
                            }
                            items(users.take(4)) { user ->
                                SearchUserResultItem(
                                    searchMemberModel = user,
                                    onClicked = onMembersItemClicked
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}