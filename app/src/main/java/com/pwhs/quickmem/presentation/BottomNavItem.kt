package com.pwhs.quickmem.presentation

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String,
) {
    data object Home : BottomNavItem(
        title = "Home",
        icon = 0,
        route = "home"
    )
}