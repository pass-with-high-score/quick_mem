package com.pwhs.quickmem.presentation.auth.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.R
import com.pwhs.quickmem.core.data.enums.LanguageCode
import com.pwhs.quickmem.presentation.auth.component.AuthButton
import com.pwhs.quickmem.presentation.auth.welcome.component.WelcomeScrollingText
import com.pwhs.quickmem.ui.theme.premiumColor
import com.pwhs.quickmem.util.gradientBackground
import com.pwhs.quickmem.util.updateLocale
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SignupScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
@Destination<RootGraph>
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(uiState.languageCode) {
        context.updateLocale(uiState.languageCode.name.lowercase())
    }
    WelCome(
        modifier = modifier,
        language = uiState.languageCode,
        onLanguageChange = { languageCode ->
            viewModel.onEvent(WelcomeUiAction.ChangeLanguage(languageCode))
        },
        onNavigateToLogin = {
            navigator.navigate(LoginScreenDestination)
        },
        onNavigateToSignup = {
            navigator.navigate(SignupScreenDestination)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WelCome(
    modifier: Modifier = Modifier,
    language: LanguageCode,
    onLanguageChange: (LanguageCode) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    val sheetLanguageState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheetLanguage by remember {
        mutableStateOf(false)
    }
    val textList = listOf(
        stringResource(R.string.txt_flashcards),
        stringResource(R.string.txt_spaced_repetition),
        stringResource(R.string.txt_study_sets),
        stringResource(R.string.txt_ai_tools)
    )
    val displayCount = 4

    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = currentIndex) {
        delay(2000)
        currentIndex = (currentIndex + 1) % textList.size
    }
    Scaffold(
        modifier = modifier
            .gradientBackground()
            .padding(horizontal = 10.dp),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bear),
                        contentDescription = stringResource(R.string.txt_bear),
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                },
                colors = topAppBarColors(
                    containerColor = Color.Transparent
                ),
                actions = {
                    ElevatedButton(
                        onClick = {
                            scope.launch {
                                showBottomSheetLanguage = true
                                sheetLanguageState.show()
                            }
                        },
                        colors = buttonColors(
                            containerColor = colorScheme.primary.copy(alpha = 0.2f),
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                when (language) {
                                    LanguageCode.VI -> stringResource(R.string.txt_vietnamese)
                                    LanguageCode.EN -> stringResource(R.string.txt_english_us)
                                },
                                style = typography.bodyMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Arrow Down"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            WelcomeScrollingText(
                textList = textList,
                displayCount = displayCount,
                currentIndex = currentIndex
            )
            val color = colorScheme.onSurface
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
                                    color = premiumColor,
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
                onClick = onNavigateToSignup,
                text = stringResource(R.string.txt_get_started_for_free)
            )
            AuthButton(
                modifier = Modifier.padding(top = 16.dp),
                onClick = onNavigateToLogin,
                text = stringResource(R.string.txt_already_have_an_account),
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
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clickable {
                            onLanguageChange(LanguageCode.VI)
                            showBottomSheetLanguage = false
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_vn_flag),
                            contentDescription = "VN Flag",
                            modifier = Modifier
                                .size(24.dp)
                        )

                        Text(
                            text = stringResource(R.string.txt_vietnamese),
                            style = typography.bodyMedium.copy(
                                color = colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .padding(start = 10.dp)
                        )
                    }
                    if (language == LanguageCode.VI) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Check",
                            tint = colorScheme.primary
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clickable {
                            onLanguageChange(LanguageCode.EN)
                            showBottomSheetLanguage = false
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_us_flag),
                            contentDescription = "US Flag",
                            modifier = Modifier
                                .size(24.dp)
                        )

                        Text(
                            text = stringResource(R.string.txt_english_us),
                            style = typography.bodyMedium.copy(
                                color = colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .padding(start = 10.dp)
                        )
                    }

                    if (language == LanguageCode.EN) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Check",
                            tint = colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}