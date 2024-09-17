package com.pwhs.quickmem.presentation.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData.AchievementsSection
import com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData.CategoriesSection
import com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData.ClassesSection
import com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData.FoldersSections
import com.pwhs.quickmem.presentation.homescreen.components.HomeHeader
import com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.hasData.SetsSections
import com.pwhs.quickmem.presentation.homescreen.components.HomeComponent.notData.StartForBegin

@Composable
fun HomeScreen(hasData: Boolean) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues = it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeHeader()
            Spacer(modifier = Modifier.height(16.dp))
            if (!hasData){
                StartForBegin()
                Spacer(modifier = Modifier.height(16.dp))
                CategoriesSection()
            }else{
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
}

@Preview
@Composable
fun PreviewHome(){
    HomeScreen(hasData = false)
}