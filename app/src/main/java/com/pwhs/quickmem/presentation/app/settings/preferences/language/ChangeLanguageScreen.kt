package com.pwhs.quickmem.presentation.app.settings.preferences.language

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.LanguageCode
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.util.updateLocale
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination<RootGraph>
@Composable
fun ChangeLanguageScreen(
    modifier: Modifier = Modifier,
    viewModel: ChangeLanguageViewModel = hiltViewModel(),
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()
    ChangeLanguage(
        modifier = modifier,
        languageCode = uiState.languageCode,
        onLanguageCodeChanged = { languageCode ->
            viewModel.onEvent(ChangeLanguageUiAction.ChangeLanguage(languageCode))
        },
        onNavigateUp = {
            resultNavigator.navigateBack(true)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChangeLanguage(
    modifier: Modifier = Modifier,
    languageCode: LanguageCode,
    onLanguageCodeChanged: (LanguageCode) -> Unit,
    onNavigateUp: () -> Unit = {}
) {
    val context = LocalContext.current
    LaunchedEffect(languageCode) {
        context.updateLocale(languageCode.name.lowercase())
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.txt_change_language),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.txt_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Card(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = languageCode == LanguageCode.EN,
                        onClick = { onLanguageCodeChanged(LanguageCode.EN) }
                    )
                    Text(
                        text = stringResource(R.string.txt_english_us),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_us_flag),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)

                    )
                }
                HorizontalDivider()
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = languageCode == LanguageCode.VI,
                        onClick = { onLanguageCodeChanged(LanguageCode.VI) }
                    )
                    Text(
                        text = stringResource(R.string.txt_vietnamese),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_vn_flag),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChangeLanguageScreenPreview() {
    QuickMemTheme {
        ChangeLanguage(
            languageCode = LanguageCode.EN,
            onLanguageCodeChanged = {}
        )
    }
}