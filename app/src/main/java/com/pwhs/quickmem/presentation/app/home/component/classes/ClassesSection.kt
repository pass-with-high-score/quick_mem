package com.pwhs.quickmem.presentation.app.home.component.classes

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClassesSection() {
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
                text = "Classes",
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
                        // Hành động khi nhấn "View more"
                    }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(end = 16.dp, start = 15.dp)
        ) {
            items(classesList) { classInfo ->
                ClassesCard(
                    title = classInfo.title,
                    classname = classInfo.className,
                    sets = classInfo.sets,
                    member = classInfo.members
                )
            }
        }
    }
}

data class ClassInfo(
    val title: String,
    val className: String,
    val sets: String,
    val members: String
)

val classesList = listOf(
    ClassInfo("ETS 2024", "Anh Ngữ MS.Hoa", "2 sets", "30 members"),
    ClassInfo("IELTS Intensive", "IELTS Fighter", "3 sets", "25 members"),
    ClassInfo("TOEIC Preparation", "TOEIC Academy", "4 sets", "40 members")
)