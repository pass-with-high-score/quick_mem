package com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R

@Composable
fun CategoriesSection() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Học phần",
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryCard(
                    "Ngôn ngữ",
                    painterResource(id = R.drawable.ic_language),
                    Color(0xFF5B46F0)
                )
                CategoryCard(
                    "Khoa học",
                    painterResource(id = R.drawable.ic_science),
                    Color(0xFF40B5AD)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryCard(
                    "Nghệ thuật",
                    painterResource(id = R.drawable.ic_art),
                    Color(0xFFFF8E5E)
                )
                CategoryCard(
                    "Toán học",
                    painterResource(id = R.drawable.ic_math),
                    Color(0xFFFFD645)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryCard(
                    "Khoa học xã hội",
                    painterResource(id = R.drawable.ic_social_science),
                    Color(0xFF5B46F0)
                )
            }
        }
    }
}

@Composable
fun CategoryCard(title: String, icon: Painter, backgroundColor: Color) {
    Surface(
        modifier = Modifier
            .width(160.dp) // Adjusted to make the cards smaller and more compact
            .height(100.dp) // Adjusted height
            .clip(RoundedCornerShape(12.dp))
            .clickable { /* Handle click here */ }
            .background(Color(0xFF2D2D44)), // Dark background for each card
        shadowElevation = 8.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp) // Larger icon box
                    .clip(RoundedCornerShape(8.dp))
                    .background(backgroundColor), // Dynamic background color based on the category
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(32.dp) // Adjust icon size
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                color = Color.White, // White text color
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            )
        }
    }
}
