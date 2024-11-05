package com.pwhs.quickmem.presentation.app.classes.add_folder

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.component.CreateTopAppBar
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun AddFolderToClassScreen(
    modifier: Modifier = Modifier,
    destination: DestinationsNavigator,
    viewModel: AddFolderToClassViewModel = hiltViewModel(),
) {
    AddFolderUI()
}

@Composable
fun AddFolderUI(
    modifier: Modifier = Modifier,
    onDoneClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    isLoading:Boolean = false
) {
    Scaffold(
        containerColor = colorScheme.background,
        modifier = modifier,
        topBar = {
            CreateTopAppBar(
                onDoneClick = onDoneClick,
                onNavigateBack = onNavigateBack,
                title = "Add folder"
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {

        }
        LoadingOverlay(
            isLoading = isLoading
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddFolderUIPreview() {
    AddFolderUI()
}