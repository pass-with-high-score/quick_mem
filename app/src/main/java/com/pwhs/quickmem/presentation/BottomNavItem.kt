package com.pwhs.quickmem.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.pwhs.quickmem.R
import com.ramcosta.composedestinations.generated.destinations.ExploreScreenDestination
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LibraryScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

sealed class BottomNavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String,
    val direction: DirectionDestinationSpec,
) {
    data object Home : BottomNavItem(
        title = R.string.txt_home,
        icon = R.drawable.ic_home,
        route = HomeScreenDestination.route,
        direction = HomeScreenDestination
    )

    data object Explore : BottomNavItem(
        title = R.string.txt_explore,
        icon = R.drawable.ic_explore,
        route = ExploreScreenDestination.route,
        direction = ExploreScreenDestination
    )

    data object Center : BottomNavItem(
        title = R.string.txt_center_fab,
        icon = R.drawable.ic_add_circle,
        route = "fab",
        direction = HomeScreenDestination
    )

    data object Library : BottomNavItem(
        title = R.string.txt_library,
        icon = R.drawable.ic_library,
        route = LibraryScreenDestination.route,
        direction = LibraryScreenDestination
    )

    data object Profile : BottomNavItem(
        title = R.string.txt_profile,
        icon = R.drawable.ic_person,
        route = ProfileScreenDestination.route,
        direction = ProfileScreenDestination
    )
}