package com.pwhs.quickmem.presentation.app.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.domain.model.settings.SettingAppearanceModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    modifier: Modifier = Modifier,
    showBottomSheet: Boolean,
    sheetState: SheetState,
    options: List<SettingAppearanceModel>,
    onOptionSelected: (SettingAppearanceModel) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            containerColor = Color.White,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Choose Option",
                    style = typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    items(options) { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .clickable {
                                    onOptionSelected(option)
                                }
                                .background(Color.LightGray)
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = option.name,
                                style = typography.bodyMedium.copy(
                                    color = colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
