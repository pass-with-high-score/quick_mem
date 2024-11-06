package com.pwhs.quickmem.presentation.app.classes.detail.members

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.domain.model.users.ClassMemberModel
import com.pwhs.quickmem.presentation.app.classes.detail.component.ClassDetailEmpty
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun MembersTabScreen(
    modifier: Modifier = Modifier,
    member: List<ClassMemberModel> = emptyList(),
    onAddMembersClicked: () -> Unit = {},
) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            when {
                member.isEmpty() -> {
                    ClassDetailEmpty(
                        modifier = Modifier.padding(innerPadding),
                        title = "This class has no folders",
                        subtitle = "Add folders to share them with your class.",
                        buttonTitle = "Add Members",
                        onAddMembersClicked = onAddMembersClicked
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(member) { member ->
                            Text(text = member.username)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MembersTabScreenPreview() {
    QuickMemTheme {
        MembersTabScreen()
    }

}