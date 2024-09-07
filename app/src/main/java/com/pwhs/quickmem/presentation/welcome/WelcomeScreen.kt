package com.pwhs.quickmem.presentation.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination<RootGraph>
fun WelcomeScreen(modifier: Modifier = Modifier) {
    val sheetLanguageState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheetLanguage by remember {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bear),
                    contentDescription = "Bear",
                    modifier = Modifier.size(30.dp),
                    tint = Color.White
                )
                ElevatedButton(
                    onClick = {},
                    modifier = Modifier
                        .width(150.dp)
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF93b3fc)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("English (US)")
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Arrow Down"
                        )
                    }
                }
            }

        }

        if (showBottomSheetLanguage) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheetLanguage = false
                },
                sheetState = sheetLanguageState,
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            sheetLanguageState.hide()
                        }.invokeOnCompletion {
                            if (!sheetLanguageState.isVisible) {
                                showBottomSheetLanguage = false
                            }
                        }
                    }
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}