package com.pwhs.quickmem.presentation

import com.pwhs.quickmem.R
import com.ramcosta.composedestinations.generated.destinations.ExploreScreenDestination
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LibraryScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.spec.BaseRoute
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String,
    val direction: DirectionDestinationSpec,
) {
    data object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.ic_home,
        route = HomeScreenDestination.route,
        direction = HomeScreenDestination
    )

    data object Explore : BottomNavItem(
        title = "Explore",
        icon = R.drawable.ic_explore,
        route = ExploreScreenDestination.route,
        direction = ExploreScreenDestination
    )

    data object Center : BottomNavItem(
        title = "",
        icon = R.drawable.ic_add_circle,
        route = "fab",
        direction = HomeScreenDestination
    )

    data object Library : BottomNavItem(
        title = "Library",
        icon = R.drawable.ic_library,
        route = LibraryScreenDestination.route,
        direction = LibraryScreenDestination
    )

    data object Profile : BottomNavItem(
        title = "Profile",
        icon = R.drawable.ic_person,
        route = ProfileScreenDestination.route,
        direction = ProfileScreenDestination
    )
}