package com.pwhs.quickmem.presentation.app.flashcard.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FlashCardTextFieldContainer(
    modifier: Modifier = Modifier,
    term: String,
    onTermChanged: (String) -> Unit,
    definition: String,
    onDefinitionChanged: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp,
            focusedElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            FlashCardTextField(
                value = term,
                onValueChange = onTermChanged,
                hint = "Term"
            )

            FlashCardTextField(
                value = definition,
                onValueChange = onDefinitionChanged,
                hint = "Definition"
            )
        }
    }
}