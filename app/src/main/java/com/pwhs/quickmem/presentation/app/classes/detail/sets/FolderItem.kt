package com.pwhs.quickmem.presentation.app.classes.detail.sets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.domain.model.users.UserResponseModel

@Composable
fun FolderItem(
    modifier: Modifier = Modifier,
    onFolderClicked: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onFolderClicked() },
        shape = RoundedCornerShape(8.dp),
    ) {

    }
}