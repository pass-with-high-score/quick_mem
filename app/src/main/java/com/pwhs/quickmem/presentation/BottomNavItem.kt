package com.pwhs.quickmem.presentation

import com.pwhs.quickmem.R
import com.ramcosta.composedestinations.generated.destinations.ExploreScreenDestination
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LibraryScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String,
) {
    data object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.ic_home,
        route = HomeScreenDestination.route
    )

    data object Explore : BottomNavItem(
        title = "Explore",
        icon = R.drawable.ic_explore,
        route = ExploreScreenDestination.route
    )

    data object Library : BottomNavItem(
        title = "Library",
        icon = R.drawable.ic_library,
        route = LibraryScreenDestination.route
    )

    data object Profile : BottomNavItem(
        title = "Profile",
        icon = R.drawable.ic_person,
        route = ProfileScreenDestination.route
    )
}