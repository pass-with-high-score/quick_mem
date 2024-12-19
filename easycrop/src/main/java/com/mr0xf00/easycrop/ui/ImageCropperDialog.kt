package com.mr0xf00.easycrop.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mr0xf00.easycrop.CropState
import com.mr0xf00.easycrop.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCropperDialog(
    modifier: Modifier = Modifier,
    state: CropState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { state.done(accept = false) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = { state.reset() }) {
                        Icon(painterResource(R.drawable.restore), null)
                    }
                    IconButton(onClick = { state.done(accept = true) }, enabled = !state.accepted) {
                        Icon(Icons.Default.Done, null)
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            CropperPreview(state = state, modifier = Modifier.fillMaxSize())
            val verticalControls =
                LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
            CropperControls(
                isVertical = verticalControls,
                state = state,
                modifier = Modifier
                    .align(if (!verticalControls) Alignment.BottomCenter else Alignment.CenterEnd)
                    .padding(12.dp),
            )
        }
    }
}


