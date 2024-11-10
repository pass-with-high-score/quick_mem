package com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.component.OpenSourceTopAppBar
import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.data.OpenSourceLicensesData
import com.pwhs.quickmem.presentation.app.settings.about.open_source_licenses.data.SourceLicensesList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun OpenSourceDetail(
    modifier: Modifier = Modifier,
    licenseId: String,
    navigator: DestinationsNavigator
) {

    if (licenseId.isEmpty()) {
        navigator.navigateUp()
        return
    }
    val license = SourceLicensesList.find { it.id == licenseId }

    if (license != null) {
        OpenSourceDetailUI(
            modifier = modifier,
            license = license,
            onNavigateBack = { navigator.navigateUp() }
        )
    } else {
        navigator.navigateUp()
    }
}

@Composable
fun OpenSourceDetailUI(
    modifier: Modifier = Modifier,
    license: OpenSourceLicensesData,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            OpenSourceTopAppBar(
                title = license.title,
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            ClickableText(
                modifier = Modifier.padding(start = 15.dp),
                text = buildAnnotatedString {
                    append(license.linkSource)
                    addStyle(
                        style = SpanStyle(color = Color.Blue),
                        start = 0,
                        end = license.linkSource.length
                    )
                },
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(license.linkSource))
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            context,
                            "Không tìm thấy ứng dụng để mở link",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewOpenSourceDetail() {
    OpenSourceDetailUI(
        license = OpenSourceLicensesData(
            id = "1",
            title = "EasyCrop",
            linkSource = "https://www.apache.org/licenses/LICENSE-2.0.txt"
        ),
        onNavigateBack = {}
    )
}
