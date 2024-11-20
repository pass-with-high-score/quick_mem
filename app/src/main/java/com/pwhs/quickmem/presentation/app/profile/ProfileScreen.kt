package com.pwhs.quickmem.presentation.app.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pwhs.quickmem.R
import com.pwhs.quickmem.presentation.app.home.HomeViewModel
import com.pwhs.quickmem.presentation.app.home.components.StreakCalendar
import com.pwhs.quickmem.presentation.app.paywall.Paywall
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.ui.theme.firasansExtraboldFont
import com.pwhs.quickmem.ui.theme.premiumColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ChoosePictureScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.revenuecat.purchases.CustomerInfo
import java.time.LocalDate

@Composable
@Destination<RootGraph>
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultBackNavigator: ResultRecipient<ChoosePictureScreenDestination, Boolean>
) {

    resultBackNavigator.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {
                // Do nothing
            }

            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(ProfileUiAction.Refresh)
                }
            }
        }
    }
    val uiState by viewModel.uiState.collectAsState()
    val homeState by homeViewModel.uiState.collectAsState()
    Profile(
        modifier = modifier,
        name = uiState.username,
        avatarUrl = uiState.userAvatar,
        isLoading = uiState.isLoading,
        onRefresh = {
            viewModel.onEvent(ProfileUiAction.Refresh)
        },
        onAvatarClick = {
            navigator.navigate(ChoosePictureScreenDestination)
        },
        navigateToSettings = {
            navigator.navigate(SettingsScreenDestination)
        },
        onCustomerInfoChanged = { customerInfo ->
            viewModel.onEvent(ProfileUiAction.OnChangeCustomerInfo(customerInfo))
        },
        customerInfo = uiState.customerInfo,
        streakCount = homeState.streakCount,
        streakDates = homeState.streakDates
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    modifier: Modifier = Modifier,
    name: String = "",
    onRefresh: () -> Unit = {},
    isLoading: Boolean = false,
    avatarUrl: String = "",
    onAvatarClick: () -> Unit = {},
    navigateToSettings: () -> Unit = {},
    onCustomerInfoChanged: (customerInfo: CustomerInfo) -> Unit = {},
    customerInfo: CustomerInfo? = null,
    onViewAllAchievement: () -> Unit = {},
    streakCount: Int = 0,
    streakDates: List<LocalDate> = emptyList(),
    currentDate: LocalDate = LocalDate.now(),
) {
    var isPaywallVisible by remember {
        mutableStateOf(false)
    }

    val refreshState = rememberPullToRefreshState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Text(
                        when (customerInfo?.activeSubscriptions?.isNotEmpty()) {
                            true -> stringResource(R.string.txt_quickmem_plus)
                            false -> stringResource(R.string.txt_quickmem)
                            null -> stringResource(R.string.txt_quickmem)
                        },
                        style = typography.titleLarge.copy(
                            fontFamily = firasansExtraboldFont,
                            color = when (customerInfo?.activeSubscriptions?.isNotEmpty()) {
                                true -> premiumColor
                                false -> colorScheme.primary
                                null -> colorScheme.primary
                            }
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                },
                title = {},
                actions = {
                    if (customerInfo?.activeSubscriptions?.isEmpty() == true) {
                        Button(
                            onClick = {
                                isPaywallVisible = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = premiumColor
                            ),
                            modifier = Modifier.padding(end = 8.dp),
                            shape = MaterialTheme.shapes.extraLarge,
                        ) {
                            Text(
                                text = stringResource(R.string.txt_upgrade),
                                style = typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = isLoading,
            state = refreshState,
            onRefresh = onRefresh
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(avatarUrl.ifEmpty { null })
                            .build(),
                        contentDescription = stringResource(R.string.txt_user_avatar),
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .size(80.dp)
                            .clip(CircleShape)
                            .clickable { onAvatarClick() },
                        contentScale = ContentScale.Crop
                    )
                }

                item {
                    Text(
                        text = name,
                        style = typography.bodyMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 24.sp
                        )
                    )
                }
                item {
                    OutlinedButton(
                        onClick = navigateToSettings,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        shape = MaterialTheme.shapes.large,
                        border = BorderStroke(
                            width = 1.dp,
                            color = colorScheme.onSurface
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = colorScheme.onSurface
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Settings,
                                    contentDescription = stringResource(R.string.txt_settings),
                                    modifier = Modifier.size(30.dp)
                                )
                                Text(
                                    text = stringResource(R.string.txt_your_settings),
                                    style = typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "Navigate to settings",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.txt_achievements),
                            style = typography.bodyLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 24.sp
                            )
                        )
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxSize(),
                        onClick = onViewAllAchievement,
                        colors = CardDefaults.cardColors(
                            containerColor = colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(0.65f)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_fire),
                                modifier = modifier.size(150.dp),
                                contentDescription = "streak fire"
                            )
                            Text(
                                text = streakCount.toString(),
                                style = typography.titleLarge.copy(
                                    color = Color(0xFFf2ac40),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 52.sp
                                )
                            )
                            Text(
                                text = "day streak",
                                style = typography.titleLarge.copy(
                                    color = Color(0xFFf2ac40),
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = "Practice every day so you don't lose your streak!",
                                modifier.padding(top = 16.dp)
                            )
                            StreakCalendar(
                                currentDate = currentDate,
                                streakDates = streakDates
                            )
                        }
                    }
                }
            }
            Paywall(
                isPaywallVisible = isPaywallVisible,
                onCustomerInfoChanged = { customerInfo ->
                    onCustomerInfoChanged(customerInfo)
                },
                onPaywallDismissed = {
                    isPaywallVisible = false
                },
            )
            LoadingOverlay(
                isLoading = isLoading
            )
        }
    }
}

@PreviewLightDark
@Composable
fun ProfilePreview() {
    QuickMemTheme {
        Profile(
            name = "John Doe",
            avatarUrl = "https://www.example.com/avatar.jpg"
        )
    }
}







