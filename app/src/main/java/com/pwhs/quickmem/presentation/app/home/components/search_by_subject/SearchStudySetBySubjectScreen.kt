package com.pwhs.quickmem.presentation.app.home.components.search_by_subject

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.presentation.app.library.study_set.component.StudySetItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Destination<RootGraph>(
    navArgs = SearchStudySetBySubjectArgs::class
)
@Composable
fun SearchStudySetBySubjectScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchStudySetBySubjectViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

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

        },
        id = uiState.id,
    )
}

@Composable
fun SearchStudySetBySubject(
    modifier: Modifier = Modifier,
    studySets: LazyPagingItems<GetStudySetResponseModel>? = null,
    onStudySetClick: (GetStudySetResponseModel?) -> Unit = {},
    id: Int,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        LazyColumn {
            item {
                BannerAds(
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = id.toString(),
                    style = typography.bodyLarge,)
            }
            items(studySets?.itemCount ?: 0) {
                val studySet = studySets?.get(it)
                StudySetItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    studySet = studySet,
                    onStudySetClick = { onStudySetClick(studySet) }
                )
            }
            item {
                if (studySets?.itemCount == 0) {
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
                Spacer(modifier = Modifier.padding(60.dp))
            }
        }
    }
}