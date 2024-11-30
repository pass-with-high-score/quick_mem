package com.pwhs.quickmem.presentation.app.deeplink.classes

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pwhs.quickmem.domain.model.classes.GetClassDetailResponseModel
import com.pwhs.quickmem.presentation.ads.BannerAds
import com.pwhs.quickmem.presentation.app.settings.component.SettingCard
import com.pwhs.quickmem.presentation.app.settings.component.SettingItem
import com.pwhs.quickmem.presentation.app.settings.component.SettingTitleSection
import com.pwhs.quickmem.presentation.component.LoadingOverlay
import com.pwhs.quickmem.util.ads.AdsUtil.interstitialAds
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.ClassDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UserDetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback
import timber.log.Timber
import com.pwhs.quickmem.R

@Destination<RootGraph>(
    navArgs = JoinClassArgs::class
)
@Composable
fun JoinClassScreen(
    modifier: Modifier = Modifier,
    viewModel: JoinClassViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is JoinClassUiEvent.JoinedClass -> {
                    if (!uiState.isFromDeepLink) {
                        navigator.navigateUp()
                    }
                    navigator.navigate(
                        ClassDetailScreenDestination(
                            id = event.id,
                            title = event.title,
                            description = event.description
                        )
                    ) {
                        if (uiState.isFromDeepLink) {
                            popUpTo(NavGraphs.root) {
                                saveState = false
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                }

                JoinClassUiEvent.UnAuthorized -> {
                    Toast.makeText(context, "Unauthorized", Toast.LENGTH_SHORT).show()
                    navigator.navigate(NavGraphs.root) {
                        popUpTo(NavGraphs.root) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
            }
        }
    }

    JoinClass(
        modifier = modifier,
        classDetailResponseModel = uiState.classDetailResponseModel,
        onJoinClass = { viewModel.onEvent(JoinClassUiAction.JoinClass) },
        onOwnerClick = {
            navigator.navigate(
                UserDetailScreenDestination(
                    userId = uiState.classDetailResponseModel?.owner?.id ?: "",
                )
            )
        },
        onBackHome = {
            navigator.navigate(NavGraphs.root) {
                popUpTo(NavGraphs.root) {
                    saveState = false
                }
                launchSingleTop = true
                restoreState = false
            }
        },
        isLoading = uiState.isLoading
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinClass(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    classDetailResponseModel: GetClassDetailResponseModel? = null,
    onJoinClass: () -> Unit = {},
    onBackHome: () -> Unit = {},
    onOwnerClick: () -> Unit = {}
) {
    var customer: CustomerInfo? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        Purchases.sharedInstance.getCustomerInfo(object : ReceiveCustomerInfoCallback {
            override fun onError(error: PurchasesError) {
                Timber.e("Error getting customer info: $error")
            }

            override fun onReceived(customerInfo: CustomerInfo) {
                Timber.d("Customer info: $customerInfo")
                customer = customerInfo
            }

        })
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.txt_join_class)) },
                navigationIcon = {
                    IconButton(
                        onClick = onBackHome
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.txt_back),
                            tint = Color.Gray.copy(alpha = 0.6f)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box {
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    SettingTitleSection(stringResource(R.string.txt_class_detail))
                    SettingCard {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            SettingItem(
                                title = "Name",
                                subtitle = classDetailResponseModel?.title ?: "",
                                showArrow = false
                            )
                            if (classDetailResponseModel?.description?.isNotEmpty() == true) {
                                HorizontalDivider()
                                SettingItem(
                                    title = stringResource(R.string.txt_description),
                                    subtitle = classDetailResponseModel.description,
                                    showArrow = false
                                )
                            }
                        }
                    }
                }

                item {
                    SettingTitleSection(stringResource(R.string.txt_owner))
                    SettingCard(
                        onClick = onOwnerClick
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row {
                                Text(
                                    text = classDetailResponseModel?.owner?.username ?: "",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.weight(1f)
                                )
                                AsyncImage(
                                    model = classDetailResponseModel?.owner?.avatarUrl ?: "",
                                    contentDescription = stringResource(R.string.txt_class_image),
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .size(20.dp)
                                        .clip(CircleShape)
                                )
                            }
                        }
                    }
                }

                item {
                    Button(
                        onClick = {
                            val isSubscribed = customer?.activeSubscriptions?.isNotEmpty() == true
                            interstitialAds(context, isSubscribed) {
                                onJoinClass()
                            }
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = stringResource(R.string.txt_join_class))
                    }
                }
                item {
                    BannerAds()
                }
            }
            LoadingOverlay(isLoading = isLoading)
        }
    }
}