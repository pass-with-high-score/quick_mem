package com.pwhs.quickmem.presentation

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pwhs.quickmem.R
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardScaffold(
    modifier: Modifier = Modifier,
    navController: NavController,
    showBottomBar: Boolean = true,
    items: List<BottomNavItem> = listOf(
        BottomNavItem.Home,
        BottomNavItem.Explore,
        BottomNavItem.Center,
        BottomNavItem.Library,
        BottomNavItem.Profile
    ),
    content: @Composable (PaddingValues) -> Unit,
) {
    val sheetLanguageState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheetCreate by remember {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(0.dp))
                        .background(colorScheme.surface),
                    containerColor = colorScheme.background,
                    contentColor = colorScheme.onBackground,
                    content = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEach { item ->
                            val color by animateColorAsState(
                                targetValue = if (currentDestination?.route?.contains(item.route) == true) {
                                    colorScheme.primary
                                } else {
                                    colorScheme.onBackground
                                },
                                label = "color_anim"
                            )
                            val iconScale by animateFloatAsState(
                                targetValue = if (currentDestination?.route?.contains(item.route) == true) {
                                    1.2f
                                } else {
                                    1f
                                },
                                label = "scale_anim"
                            )
                            NavigationBarItem(
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = colorScheme.primary,
                                    unselectedIconColor = colorScheme.onBackground,
                                    selectedTextColor = colorScheme.primary,
                                    unselectedTextColor = colorScheme.onBackground,
                                    indicatorColor = Color.Transparent,
                                ),
                                icon = {
                                    Icon(
                                        modifier = if (item.route == "center") {
                                            Modifier
                                                .size(40.dp)
                                                .scale(iconScale)
                                        } else {
                                            Modifier
                                                .size(25.dp)
                                                .scale(iconScale)
                                        },
                                        painter = painterResource(id = item.icon),
                                        contentDescription = item.title,
                                        tint = color
                                    )
                                },
                                label = {
                                    Text(
                                        text = if (item.route == "center") {
                                            ""
                                        } else {
                                            item.title
                                        },
                                        style = typography.bodySmall.copy(
                                            color = color,
                                            fontWeight = if (currentDestination?.route?.contains(
                                                    item.route
                                                ) == true
                                            ) {
                                                FontWeight.Bold
                                            } else {
                                                FontWeight.Normal
                                            }
                                        ),
                                    )
                                },
                                alwaysShowLabel = true,
                                selected = currentDestination?.route?.contains(item.route) == true,
                                onClick = {
                                    if (item.route == "center") {
                                        showBottomSheetCreate = true
                                    } else {
                                        navController.navigate(item.route) {
                                            navController.graph.startDestinationRoute?.let { screenRoute ->
                                                popUpTo(screenRoute) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        content(paddingValues)
        if (showBottomSheetCreate) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheetCreate = false
                },
                sheetState = sheetLanguageState,
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetItem(
                        title = "Flashcard set",
                        icon = R.drawable.ic_card,
                        onClick = {
                            showBottomSheetCreate = false
                        }
                    )
                    BottomSheetItem(
                        title = "Folder",
                        icon = R.drawable.ic_folder,
                        onClick = {
                            showBottomSheetCreate = false
                        }
                    )
                    BottomSheetItem(
                        title = "Create a class",
                        icon = R.drawable.ic_school,
                        onClick = {
                            showBottomSheetCreate = false
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun BottomSheetItem(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface.copy(alpha = 0.8f),
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                colorFilter = ColorFilter.tint(colorScheme.onSurface),
            )
            Text(
                text = title,
                style = typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}