package com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
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
import java.time.LocalDate

@Composable
fun AchievementsSection() {
    Column {
        Text(
            text = "Achievement",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(start = 15.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "No streaks right now",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "Calendar Icon",
                tint = Color.Gray,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Study to restart your streak",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            CalendarGrid()
        }
    }
}

@Composable
fun CalendarGrid() {
    val today = LocalDate.now()
    val yearMonth = java.time.YearMonth.of(today.year, today.month)
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfMonth = yearMonth.atDay(1)
    val dayOfWeekOfFirstDay = firstDayOfMonth.dayOfWeek.value
    val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")

    val startOffset = (dayOfWeekOfFirstDay + 5) % 7

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color.Gray
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))


    val weeks = mutableListOf<List<Int?>>()
    val firstWeek = MutableList(startOffset) { null } + (1..(7 - startOffset)).toList()
    weeks.add(firstWeek)
    var remainingDays = (8 - startOffset..daysInMonth).toList()
    while (remainingDays.isNotEmpty()) {
        weeks.add(remainingDays.take(7))
        remainingDays = remainingDays.drop(7)
    }


    weeks.forEach { week ->
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            week.forEach { day ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(30.dp)
                ) {
                    if (day != null) {
                        Text(text = day.toString(), fontSize = 14.sp, color = androidx.compose.ui.graphics.Color.Gray)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarGridPreview() {
    AchievementsSection()
}