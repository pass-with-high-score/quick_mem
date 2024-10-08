package com.pwhs.quickmem.presentation.app.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.app.home.component.achivement.AchievementsSection
import com.pwhs.quickmem.presentation.app.home.component.category.CategoriesSection
import com.pwhs.quickmem.presentation.app.home.component.classes.ClassesSection
import com.pwhs.quickmem.presentation.app.home.component.folder.FolderSections
import com.pwhs.quickmem.presentation.app.home.component.header.HomeHeader
import com.pwhs.quickmem.presentation.app.home.component.set.SetsSections
import com.pwhs.quickmem.presentation.app.home.component.startforbegin.StartForBeginSection
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                HomeUIEvent.NavigateFreeTrial -> {

                }

                HomeUIEvent.NavigateShowMoreCategories -> {

                }

                HomeUIEvent.NavigateShowMoreClasses -> {

                }

                HomeUIEvent.NavigateShowMoreFolder -> {

                }

                HomeUIEvent.NavigateShowMoreSets -> {

                }

                HomeUIEvent.NavigateToSearch -> {
                    navigator.navigate(SearchScreenDestination) {
                        popUpTo(HomeScreenDestination) {
                            inclusive = true
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
    HomeUI(
        modifier,
        viewModel,
        onNavigationSearch = {
            navigator.navigate(SearchScreenDestination) {
                popUpTo(HomeScreenDestination) {
                    inclusive = true
                    launchSingleTop = true
                }
            }
        }
    )
}

@Composable
fun HomeUI(
    modifier: Modifier,
    viewModel: HomeViewModel,
    //Home Header
    onNavigationSearch: () -> Unit,
    onOpenFreeTrailClick: () -> Unit = {},

    //SetsSection
    onSaveSets: () -> Unit = {},
    onViewAllSets: () -> Unit = {},
    onDetailSets: () -> Unit = {},
    onViewMoreSets: () -> Unit = {},

    //FolderSection
    onSaveFolder: () -> Unit = {},
    onViewAllFolder: () -> Unit = {},
    onDetailFolder: () -> Unit = {},

    ) {
    val state by viewModel.state

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HomeHeader(
                modifier = modifier,
                onOpenFreeTrailClick = onOpenFreeTrailClick,
                onNavigationIconClick = onNavigationSearch
            )
            if (state.hasData) {
                SetsSections(
                    modifier = Modifier,
                    onSaveSets = onSaveSets,
                    onDetailSets = onDetailSets
                )
                FolderSections(
                    modifier = Modifier,
                )
                ClassesSection(
                    modifier = Modifier,
                )
                AchievementsSection()
            } else {
                StartForBeginSection(
                    modifier = Modifier,
                    onClickCreateFlashcard = {},
                    onClickFindTopic = {}
                )
            }

            CategoriesSection(modifier = Modifier)
        }
    }
}
