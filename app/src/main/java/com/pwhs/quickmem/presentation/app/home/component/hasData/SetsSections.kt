package com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.presentation.homescreen.components.data.SetCard

@Preview
@Composable
fun SetsSections() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Sets",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )

            Text(
                text = "View more",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Blue,
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .padding(end = 18.dp)
                    .clickable {
                        // Handle click event
                    }
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(end = 16.dp, start = 15.dp)
    ) {
        items(3) {
            SetCard(
                title = "New Toeic 700 - Test 1 - 2024",
                terms = "23 terms",
                author = "Hadao04"
            )
        }
    }
}

@Preview
@Composable
fun PreviewSection(){
    SetsSections()
}


