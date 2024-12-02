package com.pwhs.quickmem.presentation.app.study_set.studies.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnfinishedLearningBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onKeepLearningClick: () -> Unit = {},
    onEndSessionClick: () -> Unit = {},
    sheetState: SheetState,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.txt_wait_don_t_go_yet_you_ll_miss_out_on_completing_this_study_set),
                style = typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = onKeepLearningClick,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = shapes.medium
            ) {
                Text(
                    text = stringResource(R.string.txt_keep_learning),
                    style = typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            TextButton(
                onClick = onEndSessionClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorScheme.primary,
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                shape = shapes.medium
            ) {
                Text(
                    text = stringResource(R.string.txt_end_session),
                    style = typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
