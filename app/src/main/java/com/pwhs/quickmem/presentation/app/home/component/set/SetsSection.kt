package com.pwhs.quickmem.presentation.app.home.component.set

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R

@Composable
fun SetsSections(
    modifier: Modifier,
    onSaveSets: () -> Unit = {},
    onViewAllSets: () -> Unit = {},
    onDetailSets: () -> Unit = {},
    onViewMoreOptions: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sets",
                style = typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )

            Text(
                text = "View more",
                style = typography.bodyMedium.copy(
                    color = Color.Blue,
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .padding(end = 18.dp)
                    .clickable {
                        onViewAllSets
                    }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(setsList) { set ->
                SetCard(
                    setInfo = set,
                    imageResId = R.drawable.ic_facebook,
                    onSaveSets = { onSaveSets },
                    onMoreOption = { onViewMoreOptions },
                    onDetailSets = { onDetailSets }
                )
            }
        }
    }
}

data class SetInfo(
    val title: String,
    val terms: String,
    val author: String,
)

val setsList = listOf(
    SetInfo("New Toeic 700 - Test 1 - 2024", "23 terms", "Hadao04"),
    SetInfo("IELTS Reading Practice", "50 terms", "IELTS Fighter"),
    SetInfo("Advanced Physics", "45 terms", "SciGuy999")
)