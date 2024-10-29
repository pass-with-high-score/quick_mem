package com.pwhs.quickmem.presentation.app.folder.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.app.folder.detail.SortOptionEnum

@Composable
fun FolderSortOptionTextButton(
    modifier: Modifier = Modifier,
    currentSortOption: SortOptionEnum,
    onSortOptionClicked: () -> Unit
) {
    Row (
        modifier = modifier.padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currentSortOption.sortOption,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.clickable { onSortOptionClicked() }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_unfold_more),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
                .padding(start = 3.dp)
                .clickable { onSortOptionClicked() },
            tint = Color.Black
        )
    }
}
