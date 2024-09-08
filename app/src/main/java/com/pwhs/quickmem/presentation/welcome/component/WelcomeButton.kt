package com.pwhs.quickmem.presentation.welcome.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.ui.theme.blue

@Composable
fun WelComeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: Color = blue,
    borderColor: Color = Color.Transparent,
    textColor: Color = Color.White,
    text: String
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 5.dp,
            pressedElevation = 5.dp,
            focusedElevation = 5.dp,
            hoveredElevation = 5.dp,
            disabledElevation = 5.dp
        )
    ) {
        Text(
            text, style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = textColor
            )
        )
    }
}