package com.pwhs.quickmem.presentation.app.classes.detail.members

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.users.ClassMemberModel
import com.pwhs.quickmem.presentation.app.classes.detail.component.ClassDetailEmpty
import com.pwhs.quickmem.presentation.app.classes.detail.members.component.ClassMemberItem
import com.pwhs.quickmem.ui.theme.QuickMemTheme

@Composable
fun MembersTabScreen(
    modifier: Modifier = Modifier,
    isOwner: Boolean,
    member: List<ClassMemberModel> = emptyList(),
    onMembersItemClicked: (ClassMemberModel) -> Unit = {},
    onAddMembersClicked: () -> Unit = {},
    onDeletedClicked: (String) -> Unit = {}
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
                        title = stringResource(R.string.txt_this_class_has_no_members),
                        subtitle = stringResource(R.string.txt_add_members_to_share_them_with_your_class),
                        buttonTitle = stringResource(R.string.txt_add_members),
                        onAddClick = onAddMembersClicked,
                        isOwner = isOwner
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        items(member) { member ->
                            ClassMemberItem(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                classMemberModel = member,
                                onClicked = onMembersItemClicked,
                                canDelete = isOwner,
                                onDeleteClicked = onDeletedClicked,
                            )
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
        MembersTabScreen(isOwner = false)
    }

}