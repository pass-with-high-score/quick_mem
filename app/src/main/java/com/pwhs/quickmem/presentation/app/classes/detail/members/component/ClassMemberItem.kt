package com.pwhs.quickmem.presentation.app.classes.detail.members.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pwhs.quickmem.domain.model.users.ClassMemberModel
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun ClassMemberItem(
    modifier: Modifier = Modifier,
    onClicked: (ClassMemberModel) -> Unit = {},
    classMemberModel: ClassMemberModel,
    canDelete: Boolean = false,
    onDeleteClicked: (String) -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        onClick = { onClicked(classMemberModel) },
        border = BorderStroke(
            width = 1.dp,
            color = colorScheme.onSurface.copy(alpha = 0.12f)
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        model = classMemberModel.avatarUrl,
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        classMemberModel.username,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
                if (classMemberModel.isOwner) {
                    Text(
                        text = "Owner",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = colorScheme.primary
                        ),
                    )
                }

                if (canDelete && !classMemberModel.isOwner) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "delete",
                        modifier = Modifier.size(24.dp)
                            .clickable {
                                onDeleteClicked(classMemberModel.id)
                            }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ClassMemberItemPreview() {
    QuickMemTheme {
        Scaffold {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                items(1) {
                    ClassMemberItem(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        classMemberModel = ClassMemberModel(
                            id = "id",
                            username = "username",
                            avatarUrl = "",
                            isOwner = true,
                            role = "student",
                        ),
                        canDelete = false
                    )
                }
                items(10) {
                    ClassMemberItem(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        classMemberModel = ClassMemberModel(
                            id = "id",
                            username = "username",
                            avatarUrl = "",
                            isOwner = false,
                            role = "student",
                        ),
                        canDelete = true
                    )
                }
            }
        }
    }
}