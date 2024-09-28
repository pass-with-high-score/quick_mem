package com.pwhs.quickmem.presentation.app.home.component.achivement

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
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun AchievementsSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Achievement",
            style = typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(start = 15.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No streaks right now",
                style = typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            )

            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "Calendar Icon",
                tint = Color.Gray,
                modifier = Modifier.size(40.dp)
            )

            Text(
                text = "Study to restart your streak",
                style = typography.bodyMedium.copy(
                    fontSize = 16.sp
                )
            )

            Spacer(Modifier.height(20.dp))
            CalendarGrid()
        }
    }
}


@Composable
fun CalendarGrid() {
    val today = LocalDate.now()
    val yearMonth = YearMonth.of(today.year, today.month)
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfMonth = yearMonth.atDay(1)
    val dayOfWeekOfFirstDay = firstDayOfMonth.dayOfWeek.value
    val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")

    val startOffset = (dayOfWeekOfFirstDay + 5) % 7

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Tạo lịch
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
                        Text(
                            text = day.toString(),
                            fontSize = 14.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
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