package com.pwhs.quickmem.presentation.app.report

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Destination<RootGraph>
@Composable
fun ReportScreen(
    reportType: ReportTypeEnum,
    userID: String,
    userName: String,
    navigator: DestinationsNavigator,
) {
    var selectedReason by remember { mutableStateOf("") }
    val context = LocalContext.current

    Report(
        title = reportType.title,
        questionText = reportType.questionText,
        options = reportType.options,
        selectedReason = selectedReason,
        onReasonSelected = { selectedReason = it },
        onContinue = {
            if (selectedReason.isNotEmpty()) {
                val body = """
                Reason for reporting: $selectedReason
                User ID: $userID
                User Name: $userName
                """.trimIndent()
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:report@quickmem.app")
                    putExtra(Intent.EXTRA_SUBJECT, "Report")
                    putExtra(Intent.EXTRA_TEXT, body)
                }
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                } else {
                    Toast.makeText(
                        context,
                        "There is no email application on this device.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(context, "Please select a reason for reporting.", Toast.LENGTH_SHORT)
                    .show()
            }
        },
        onBackClick = { navigator.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Report(
    modifier: Modifier = Modifier,
    title: String,
    questionText: String,
    options: List<String>,
    selectedReason: String,
    onReasonSelected: (String) -> Unit,
    onContinue: () -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 35.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            fontSize = 20.sp
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = questionText,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            options.forEach { reason ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onReasonSelected(reason) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedReason == reason,
                        onClick = { onReasonSelected(reason) }
                    )
                    Text(
                        text = reason,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = onContinue,
                modifier = Modifier
                    .padding(vertical = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedReason.isEmpty()) Color.Gray else MaterialTheme.colorScheme.primary
                ),
                shape = MaterialTheme.shapes.extraLarge,
                enabled = selectedReason.isNotEmpty()
            ) {
                Text(
                    text = "Continue",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun ReportScreenPreview() {
    QuickMemTheme {
        Report(
            title = "Report this user",
            questionText = "Why are you reporting this user?",
            options = listOf("Option 1", "Option 2", "Option 3"),
            selectedReason = "",
            onReasonSelected = {},
            onContinue = {},
            onBackClick = {}
        )
    }
}
