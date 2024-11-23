package com.pwhs.quickmem.presentation.app.home.search_by_subject

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.presentation.app.home.search_by_subject.component.SearchStudySetBySubjectTopAppBar
import com.pwhs.quickmem.presentation.app.library.component.SearchTextField
import com.pwhs.quickmem.presentation.app.library.study_set.component.StudySetItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination.invoke
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlin.collections.filter

@Destination<RootGraph>(
    navArgs = SearchStudySetBySubjectArgs::class
)
@Composable
fun SearchStudySetBySubjectScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchStudySetBySubjectViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultBackNavigator: ResultBackNavigator<Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val studySetItems: LazyPagingItems<GetStudySetResponseModel> =
        viewModel.studySetState.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SearchStudySetBySubjectUiEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    SearchStudySetBySubject(
        modifier = modifier,
        onStudySetClick = { studySet ->
            navigator.navigate(
                StudySetDetailScreenDestination(
                    id = (studySet?.id ?: 0).toString(),
                    code = ""
                )
            )
        },
        nameSubject = uiState.subject?.name ?: "",
        colorSubject = uiState.subject?.color ?: Color.Blue,
        descriptionSubject = uiState.subject?.description ?: "",
        studySets = studySetItems,
        onNavigateBack = {
            resultBackNavigator.navigateBack(true)
        },
        onStudySetRefresh = {
            viewModel.onEvent(SearchStudySetBySubjectUiAction.RefreshStudySets)
        }
    )
}

@Composable
fun SearchStudySetBySubject(
    modifier: Modifier = Modifier,
    studySets: LazyPagingItems<GetStudySetResponseModel>? = null,
    onStudySetClick: (GetStudySetResponseModel?) -> Unit = {},
    nameSubject: String = "",
    colorSubject: Color,
    descriptionSubject: String = "",
    onNavigateBack: () -> Unit,
    onStudySetRefresh: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredStudySets = studySets?.itemSnapshotList?.items?.filter {
        searchQuery.trim().takeIf { query -> query.isNotEmpty() }?.let { query ->
            it.title.contains(query, ignoreCase = true)
        } ?: true
    }

    Scaffold(
        containerColor = colorScheme.background,
        modifier = modifier,
        topBar = {
            SearchStudySetBySubjectTopAppBar(
                onNavigateBack = onNavigateBack,
                name = "Subject \"${nameSubject}\"",
                color = colorSubject,
                description = descriptionSubject
            )
        }
    ) { innerPadding ->
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                LazyColumn {
                    item {
                        SearchTextField(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it },
                            placeholder = stringResource(R.string.txt_search_study_sets)
                        )
                    }
                    item {
                        BannerAds(
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    items(filteredStudySets?.size ?: 0) { index ->
                        val studySet = filteredStudySets?.get(index)
                        StudySetItem(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            studySet = studySet,
                            onStudySetClick = { onStudySetClick(studySet) }
                        )
                    }
                    item {
                        if (filteredStudySets?.isEmpty() == true) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(innerPadding)
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.txt_no_study_sets_found),
                                    style = typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    item {
                        studySets?.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(innerPadding)
                                            .padding(top = 40.dp)
                                            .padding(horizontal = 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(36.dp),
                                            color = colorScheme.primary
                                        )
                                    }
                                }
                                loadState.refresh is LoadState.Error -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(innerPadding)
                                            .padding(top = 40.dp)
                                            .padding(horizontal = 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Image(
                                            imageVector = Icons.Default.Error,
                                            contentDescription = stringResource(R.string.txt_error),
                                        )
                                        Text(
                                            text = stringResource(R.string.txt_error_occurred),
                                            style = typography.titleLarge,
                                            textAlign = TextAlign.Center
                                        )
                                        Button(
                                            onClick = onStudySetRefresh,
                                            modifier = Modifier.padding(top = 16.dp)
                                        ) {
                                            Text(text = "Retry")
                                        }
                                    }
                                }
                                loadState.append is LoadState.Loading -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(innerPadding)
                                            .padding(top = 40.dp)
                                            .padding(horizontal = 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(36.dp),
                                            color = colorScheme.primary
                                        )
                                    }
                                }
                                loadState.append is LoadState.Error -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(innerPadding)
                                            .padding(top = 40.dp)
                                            .padding(horizontal = 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Image(
                                            imageVector = Icons.Default.Error,
                                            contentDescription = stringResource(R.string.txt_error),
                                        )
                                        Text(
                                            text = stringResource(R.string.txt_error_occurred),
                                            style = typography.titleLarge,
                                            textAlign = TextAlign.Center
                                        )
                                        Button(
                                            onClick = { retry() },
                                            modifier = Modifier.padding(top = 16.dp)
                                        ) {
                                            Text(text = "Retry")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.padding(60.dp))
                    }
                }
            }
        }
    }
}