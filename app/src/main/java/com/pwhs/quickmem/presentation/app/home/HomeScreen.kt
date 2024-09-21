package com.pwhs.quickmem.presentation.app.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.rememberScrollState
import com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData.AchievementsSection
import com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData.CategoriesSection
import com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData.ClassesSection
import com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData.FoldersSections
import com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData.SetsSections
import com.pwhs.quickmem.presentation.homescreen.components.HomeHeader
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination<RootGraph>
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeHeader(navigator = navigator)
            Spacer(modifier = Modifier.height(16.dp))

            // Fetch and display data from ViewModel
            SetsSections()
            Spacer(modifier = Modifier.height(16.dp))

            FoldersSections()
            Spacer(modifier = Modifier.height(16.dp))

            ClassesSection()
            Spacer(modifier = Modifier.height(16.dp))

            AchievementsSection()
            Spacer(modifier = Modifier.height(16.dp))

            CategoriesSection()
        }
    }
}
