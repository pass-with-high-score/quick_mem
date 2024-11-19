package com.pwhs.quickmem.presentation.app.classes.remove_members.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.domain.model.users.ClassMemberModel

@Composable
fun RemoveMembersList(
    modifier: Modifier = Modifier,
    members: List<ClassMemberModel> = emptyList(),
    onRemoveMembers: (String) -> Unit = {},
    membersRemoveIds: List<String> = emptyList(),
) {
    val searchQuery by remember { mutableStateOf("") }

    val filteredMembers = members.filter {
        searchQuery.trim().takeIf { query -> query.isNotEmpty() }?.let { query ->
            it.username.contains(query, ignoreCase = true)
        } ?: true
    }

    Box(modifier = modifier) {
        if (members.isEmpty()) {
            Text(
                text = "No members available",
                style = typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {
                filteredMembers.forEach { member ->
                    val isRemove = member.id in membersRemoveIds
                    RemoveMembersItems(
                        modifier = Modifier.fillMaxWidth(),
                        member = member,
                        isRemove = isRemove,
                        onRemoveMembers = {
                            onRemoveMembers(member.id)
                        }
                    )
                }
            }
        }
    }
}
