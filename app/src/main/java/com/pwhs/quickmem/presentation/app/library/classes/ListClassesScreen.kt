package com.pwhs.quickmem.presentation.app.library.classes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.presentation.app.library.classes.component.ClassItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListClassesScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    classes: List<GetClassByOwnerResponseModel> = emptyList(),
    onClassClicked: (String) -> Unit = {},
    onAddClassClick: () -> Unit = {},
    onClassRefresh: () -> Unit = {},
) {
    val refreshState = rememberPullToRefreshState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            state = refreshState,
            isRefreshing = isLoading,
            onRefresh = {
                onClassRefresh()
            }
        ) {
            when {
                classes.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(top = 40.dp)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.GroupAdd,
                            contentDescription = "Folder",
                            modifier = Modifier.size(60.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Group study materials to save time and share with others QuickMem members",
                            style = typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            ),
                        )
                        Button(
                            shape = MaterialTheme.shapes.medium,
                            onClick = onAddClassClick,
                            modifier = Modifier
                                .width(150.dp)
                        ) {
                            Text(
                                "Create a class",
                                style = typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(horizontal = 16.dp),
                    ) {
                        LazyColumn {
                            item {
                                // TODO: Add search bar
                            }
                            items(classes) { classItem ->
                                ClassItem(
                                    classItem = classItem,
                                    onClick = { onClassClicked(classItem.id) }
                                )
                            }
                            item {
                                BannerAds(
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.padding(60.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}