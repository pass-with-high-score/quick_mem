package com.pwhs.quickmem.presentation.app.study_set.studies.true_false

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(
    navArgs = LearnByTrueFalseArgs::class
)
@Composable
fun LearnByTrueFalseScreen(
    modifier: Modifier = Modifier,
    viewModel: LearnByTrueFalseViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
   LearnByTrueFalse()
}

@Composable
fun LearnByTrueFalse(modifier: Modifier = Modifier) {
    Scaffold { innerPadding ->
       Column(
           modifier = modifier.padding(innerPadding),
       ) {  }
    }
}

@Preview
@Composable
private fun LearnByTrueFalseScreenPreview() {
    QuickMemTheme {

    }
}