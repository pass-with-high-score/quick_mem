package com.pwhs.quickmem.presentation.auth.welcome

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.auth.welcome.component.WelcomeScrollingText
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SignupScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination<RootGraph>
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator
) {
    val sheetLanguageState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheetLanguage by remember {
        mutableStateOf(false)
    }
    val textList = listOf(
        "Flashcards",
        "Spaced Repetition",
        "Study Sets",
        "AI Tools"
    )
    val displayCount = 4

    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = currentIndex) {
        delay(2000)
        currentIndex = (currentIndex + 1) % textList.size
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
                    onClick = {
                        scope.launch {
                            showBottomSheetLanguage = true
                            sheetLanguageState.show()
                        }
                    },
                    modifier = Modifier
                        .width(140.dp)
                        .height(50.dp),
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

            WelcomeScrollingText(
                textList = textList,
                displayCount = displayCount,
                currentIndex = currentIndex
            )
            val color = MaterialTheme.colorScheme.onSurface
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = ParagraphStyle(
                            textAlign = TextAlign.Start,
                            lineHeight = 40.sp
                        )
                    ) {
                        withStyle(
                            style = SpanStyle(
                                color = color,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Black
                            )
                        ) {
                            append("All the tools for \nlearning success.\n")
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFFfa6c3e),
                                )
                            ) {
                                append("In one app.")
                            }
                        }
                    }
                },
                modifier = Modifier.padding(top = 60.dp)
            )

            AuthButton(
                modifier = Modifier.padding(top = 50.dp),
                onClick = {
                    navigator.navigate(SignupScreenDestination)
                },
                text = "Get started for free"
            )
            AuthButton(
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    navigator.navigate(LoginScreenDestination)
                },
                text = "Already have an account",
                colors = Color.White,
                borderColor = colorScheme.primary,
                textColor = colorScheme.primary
            )
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