package com.pwhs.quickmem.presentation.app.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.subject.SubjectModel

@Composable
fun SubjectItem(
    modifier: Modifier = Modifier,
    onSearchStudySetBySubject: (id: Int) -> Unit,
    subject: SubjectModel,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = { onSearchStudySetBySubject(subject.id) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = subject.iconRes!!),
                    contentDescription = subject.name,
                    tint = subject.color!!,
                    modifier = Modifier
                        .size(35.dp)
                )

                Text(
                    text = subject.name,
                    style = typography.titleMedium.copy(
                        color = colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                )

                Text(
                    text = when (subject.studySetCount) {
                        1 -> "${subject.studySetCount} Study Set"
                        else -> "${subject.studySetCount} Study Sets"
                    },
                    style = typography.bodyMedium.copy(
                        color = colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
fun SubjectItemPreview() {
    SubjectItem(
        onSearchStudySetBySubject = {},
        subject = SubjectModel(
            id = 1,
            name = "All",
            iconRes = R.drawable.ic_all,
            color = Color(0xFF7f60f9),
            description = "Agriculture is the study of farming and cultivation of land."
        ),
    )
}
