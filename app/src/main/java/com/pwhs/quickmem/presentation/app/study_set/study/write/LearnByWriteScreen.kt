package com.pwhs.quickmem.presentation.app.study_set.study.write

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun LearnByWriteScreen(
    modifier: Modifier = Modifier,
    viewModel: LearnByWriteViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    Text(text = stringResource(R.string.txt_learnbywrite))
}

@Preview
@Composable
private fun LearnByWriteScreenPreview() {
    QuickMemTheme {

    }
}