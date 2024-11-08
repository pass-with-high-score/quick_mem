package com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.component.OpenSourceTopAppBar
import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.data.OpenSourceLicensesData
import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.data.SourceLicensesList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun OpenSourceScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: OpenSourceViewModel = hiltViewModel()
) {
    OpenSourceLicensesUI(
        licenses = SourceLicensesList,
        onNavigateBack = {
            navigator.navigateUp()
        }
    )
}

@Composable
fun OpenSourceLicensesUI(
    modifier: Modifier = Modifier,
    title: String = "Open Source Licenses",
    onNavigateBack: () -> Unit = {},
    licenses: List<OpenSourceLicensesData>
) {
    Scaffold(
        topBar = {
            OpenSourceTopAppBar(
                modifier = modifier,
                title = title,
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding)
        ) {
            items(licenses) { license ->
                LicenseItem(license)
            }
        }
    }
}

@Composable
fun LicenseItem(license: OpenSourceLicensesData) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = license.title,
                style = MaterialTheme.typography.titleMedium
            )
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append(license.linkSource)
                    }
                },
                onClick = {
                    if (license.linkSource.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(license.linkSource))
                        context.startActivity(intent)
                    }
                }
            )
        }
    }
}
