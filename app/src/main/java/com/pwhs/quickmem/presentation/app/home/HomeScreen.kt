package com.pwhs.quickmem.presentation.app.home

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.pwhs.quickmem.R
import com.pwhs.quickmem.domain.model.notification.GetNotificationResponseModel
import com.pwhs.quickmem.presentation.app.home.components.NotificationListBottomSheet
import com.pwhs.quickmem.presentation.app.paywall.Paywall
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.ui.theme.firasansExtraboldFont
import com.pwhs.quickmem.ui.theme.premiumColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.revenuecat.purchases.CustomerInfo

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Destination<RootGraph>
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {

                else -> {}
            }
        }
    }
    Home(
        modifier = modifier,
        streakCount = uiState.streakCount,
        notificationCount = uiState.notificationCount,
        onNavigateToSearch = {
            navigator.navigate(SearchScreenDestination)
        },
        onNotificationEnabled = { isEnabled ->
            viewModel.onEvent(HomeUiAction.OnChangeAppPushNotifications(isEnabled))
        },
        customer = uiState.customerInfo,
        onCustomerInfoChanged = { customerInfo ->
            viewModel.onEvent(HomeUiAction.OnChangeCustomerInfo(customerInfo))
        },
        notifications = uiState.notifications,
        onNotificationClicked = { notificationId ->
            viewModel.onEvent(HomeUiAction.MarkAsRead(notificationId))
        }
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
private fun Home(
    modifier: Modifier = Modifier,
    streakCount: Int = 0,
    notificationCount: Int = 0,
    onNavigateToSearch: () -> Unit = {},
    onNotificationEnabled: (Boolean) -> Unit = {},
    customer: CustomerInfo? = null,
    onCustomerInfoChanged: (CustomerInfo) -> Unit = {},
    onNotificationClicked: (String) -> Unit = {},
    notifications: List<GetNotificationResponseModel> = emptyList(),
) {

    var showNotificationBottomSheet by remember { mutableStateOf(false) }
    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    val streakBottomSheet = rememberModalBottomSheetState()
    var showStreakBottomSheet by remember {
        mutableStateOf(false)
    }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fire_streak))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
    val notificationPermission =
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    LaunchedEffect(notificationPermission) {
        if (!notificationPermission.status.isGranted) {
            notificationPermission.launchPermissionRequest()
            onNotificationEnabled(false)
        } else {
            onNotificationEnabled(true)
        }
    }
    var isPaywallVisible by remember {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.primary.copy(alpha = 0.5f),
                ),
                navigationIcon = {
                    Text(
                        when (customer?.activeSubscriptions?.isNotEmpty()) {
                            true -> stringResource(R.string.txt_quickmem_plus)
                            false -> stringResource(R.string.txt_quickmem)
                            null -> stringResource(R.string.txt_quickmem)
                        },
                        style = typography.titleLarge.copy(
                            fontFamily = firasansExtraboldFont,
                            color = when (customer?.activeSubscriptions?.isNotEmpty()) {
                                true -> premiumColor
                                false -> colorScheme.primary
                                null -> colorScheme.secondary
                            }
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                },
                expandedHeight = 140.dp,
                collapsedHeight = 56.dp,
                title = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(end = 16.dp, bottom = 8.dp),
                        shape = CircleShape,
                        onClick = onNavigateToSearch,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = stringResource(R.string.txt_search),
                                tint = colorScheme.secondary,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = stringResource(R.string.txt_study_sets_folders_class),
                                style = typography.bodyMedium.copy(
                                    color = colorScheme.secondary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                },
                actions = {
                    if (customer?.activeSubscriptions?.isEmpty() == true) {
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
                    IconButton(
                        onClick = { showNotificationBottomSheet = true },
                    ) {
                        Box {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = stringResource(R.string.txt_notifications),
                                tint = colorScheme.onPrimary,
                                modifier = Modifier.size(30.dp)
                            )
                            if (notificationCount > 0) {
                                Badge(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(16.dp),
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "$notificationCount",
                                            style = typography.bodySmall.copy(
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Start,
        bottomBar = {
            Spacer(modifier = Modifier.height(100.dp))
        },
        floatingActionButton = {
            Card(
                onClick = {
                    showStreakBottomSheet = true
                },
                shape = CircleShape
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(5.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_fire),
                        contentDescription = stringResource(R.string.txt_streak),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = "$streakCount",
                        style = typography.titleLarge.copy(
                            color = colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("Home Screen")
            Text(
                text = "Has active subscription - ${customer?.activeSubscriptions?.isNotEmpty()}"
            )
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
    if (showStreakBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showStreakBottomSheet = false
            },
            sheetState = streakBottomSheet,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LottieAnimation(
                        composition = composition,
                        progress = { progress },
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                    )
                    Text(
                        text = stringResource(R.string.txt_streak_count, streakCount),
                        style = typography.titleLarge.copy(
                            color = colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }

    if (showNotificationBottomSheet) {
        NotificationListBottomSheet(
            onDismissRequest = { showNotificationBottomSheet = false },
            notifications = notifications,
            onNotificationClicked = onNotificationClicked,
            sheetState = modalBottomSheetState
        )
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
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
private fun HomeScreenPreview() {
    QuickMemTheme {
        Home(
            streakCount = 5
        )
    }
}
