package com.pwhs.quickmem.presentation.app.home.component.startforbegin

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R

@Composable
fun StartForBeginSection(
    modifier: Modifier,
    onClickCreateFlashcard: () -> Unit,
    onClickFindTopic: () -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = "Start for begin",
            style = typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Start
        )

        ActionButton(
            title = "Create a flashcard for yourself",
            iconResId = R.drawable.onboarding1,
            onClick = {
                onClickCreateFlashcard
            }
        )

        Spacer(modifier = modifier.height(16.dp))

        ActionButton(
            title = "Find a topic, class",
            iconResId = R.drawable.ic_launcher_foreground,
            onClick = {
                onClickFindTopic
            }
        )
    }
}

@Composable
fun ActionButton(
    title: String,
    @DrawableRes iconResId: Int,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF0F0F0))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )
    }
}