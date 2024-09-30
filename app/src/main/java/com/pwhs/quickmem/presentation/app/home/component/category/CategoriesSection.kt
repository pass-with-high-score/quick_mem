package com.pwhs.quickmem.presentation.app.home.component.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R

@Composable
fun CategoriesSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier.fillMaxWidth().padding(start = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Categories",
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

                    }
            )
        }
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    iconResId = category.first,
                    title = category.second,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

val categories = listOf(
    Pair(R.drawable.ic_language, "Language"),
    Pair(R.drawable.ic_science, "Science"),
    Pair(R.drawable.ic_art, "Art"),
    Pair(R.drawable.ic_math, "Math"),
    Pair(R.drawable.ic_social_science, "Social Science")
)
