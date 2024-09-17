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
    data object Solution : BottomNavItem(
        title = "Solution",
        icon = 0,
        route = "solution"
    )
    data object Library : BottomNavItem(
        title = "Library",
        icon = 0,
        route = "library"
    )
    data object Profile : BottomNavItem(
        title = "Profile",
        icon = 0,
        route = "profile"
    )
}