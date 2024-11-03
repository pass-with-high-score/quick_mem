package com.pwhs.quickmem.presentation.app.folder.add_study_set

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.folder.add_study_set.component.AddStudySetTopAppBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun AddStudySetScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: AddStudySetViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {

                else -> {}
            }
        }
    }
    AddStudySet(modifier = modifier)
}

@Composable
fun AddStudySet(
    modifier: Modifier = Modifier,
    onDoneClick: () -> Unit = {},
    onNavigateCancel: () -> Unit = {},
    onAddStudySetClicked: () -> Unit = {}
) {
    Scaffold(
        containerColor = colorScheme.background,
        modifier = modifier,
        topBar = {
            AddStudySetTopAppBar(
                onDoneClick = onDoneClick,
                onNavigateCancel = onNavigateCancel,
                title = "Add Study Set"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = onAddStudySetClicked
            ) {
                Text("Add Study Set")
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AddStudySetPreview() {
    AddStudySet()
}