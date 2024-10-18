package com.pwhs.quickmem.presentation.app.study_set.detail.material

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage

@Composable
fun calculateDynamicFontSize(text: String): androidx.compose.ui.unit.TextUnit {
    return when {
        text.length <= 20 -> 24.sp
        text.length <= 50 -> 20.sp
        else -> 16.sp
    }
}

@Composable
fun FlipCard(
    frontText: String,
    backText: String,
    backImage: String? = null,
    backgroundColor: Color
) {

    var isFlipped by remember { mutableStateOf(false) }
    var isAnimationFinished by remember { mutableStateOf(true) }

    // Tính toán kích cỡ chữ dựa trên độ dài của văn bản
    val frontTextSize = calculateDynamicFontSize(frontText)
    val backTextSize = calculateDynamicFontSize(backText)

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = screenWidth * 0.9f

    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(500),
        finishedListener = {
            isAnimationFinished = true
        }
    )

    Card(
        modifier = Modifier
            .height(220.dp)
            .width(cardWidth)
            .padding(10.dp)
            .graphicsLayer {
                rotationX = rotation
                cameraDistance = 8 * density
            }
            .clickable(enabled = isAnimationFinished) {
                if (isAnimationFinished) {
                    isAnimationFinished = false
                    isFlipped = !isFlipped
                }
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 12.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = Color.White
        ),
    ) {
        if (rotation <= 90f) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .graphicsLayer {
                        rotationX = 0f
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = frontText,
                    fontSize = frontTextSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .graphicsLayer {
                        rotationX = 180f
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = backText,
                    fontSize = backTextSize,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                backImage?.let {
                    if (it.isNotEmpty()) {
                        AsyncImage(
                            model = it,
                            contentDescription = null,
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                                .padding(8.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}