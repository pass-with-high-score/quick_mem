package com.pwhs.quickmem.presentation.app.classes.remove_members.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.users.ClassMemberModel

@Composable
fun RemoveMembersItems(
    modifier: Modifier = Modifier,
    member: ClassMemberModel,
    isRemove: Boolean = false,
    onRemoveMembers: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorScheme.onSurface.copy(alpha = 0.12f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Transparent)
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = member.avatarUrl,
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column (
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        Text(
                            text = member.username,
                            style = typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Text(
                            text = "Role: ${member.role}",
                            style = typography.bodyMedium
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable {
                        onRemoveMembers()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(if (isRemove) R.drawable.ic_check_circle else R.drawable.ic_add_circle),
                    contentDescription = if (isRemove) "Remove Icon" else "Add Icon",
                    modifier = Modifier.size(26.dp),
                    tint = colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRemoveMembersItem() {
    val sampleMember = ClassMemberModel(
        id = "1",
        username = "John Doe",
        avatarUrl = "https://example.com/avatar.jpg",
        isOwner = false,
        role = "Student"
    )

    RemoveMembersItems(
        member = sampleMember,
        isRemove = false
    )
}
