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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import com.pwhs.quickmem.domain.model.classes.GetClassByOwnerResponseModel
import com.pwhs.quickmem.domain.model.folder.GetFolderResponseModel
import com.pwhs.quickmem.domain.model.notification.GetNotificationResponseModel
import com.pwhs.quickmem.domain.model.study_set.GetStudySetResponseModel
import com.pwhs.quickmem.domain.model.subject.SubjectModel
import com.pwhs.quickmem.presentation.app.home.components.ClassHomeItem
import com.pwhs.quickmem.presentation.app.home.components.FolderHomeItem
import com.pwhs.quickmem.presentation.app.home.components.NotificationListBottomSheet
import com.pwhs.quickmem.presentation.app.home.components.StreakCalendar
import com.pwhs.quickmem.presentation.app.home.components.StudySetHomeItem
import com.pwhs.quickmem.presentation.app.home.components.SubjectItem
import com.pwhs.quickmem.presentation.app.library.classes.component.ClassItem
import com.pwhs.quickmem.presentation.app.library.folder.component.FolderItem
import com.pwhs.quickmem.presentation.app.library.study_set.component.StudySetItem
import com.pwhs.quickmem.presentation.app.paywall.Paywall
import com.pwhs.quickmem.ui.theme.QuickMemTheme
import com.pwhs.quickmem.ui.theme.firasansExtraboldFont
import com.pwhs.quickmem.ui.theme.premiumColor
import com.pwhs.quickmem.ui.theme.streakTextColor
import com.pwhs.quickmem.ui.theme.streakTitleColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ClassDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ClassDetailScreenDestination.invoke
import com.ramcosta.composedestinations.generated.destinations.CreateStudySetScreenDestination
import com.ramcosta.composedestinations.generated.destinations.CreateStudySetScreenDestination.invoke
import com.ramcosta.composedestinations.generated.destinations.FolderDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FolderDetailScreenDestination.invoke
import com.ramcosta.composedestinations.generated.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SearchStudySetBySubjectScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination
import com.ramcosta.composedestinations.generated.destinations.StudySetDetailScreenDestination.invoke
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.revenuecat.purchases.CustomerInfo
import java.time.LocalDate

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
        isLoading = uiState.isLoading,
        modifier = modifier,
        subjects = uiState.subjects,
        studySets = uiState.studySets,
        folders = uiState.folders,
        classes = uiState.classes,
        streakCount = uiState.streakCount,
        streakDates = uiState.streakDates,
        notificationCount = uiState.notificationCount,
        onStudySetClick = {
            navigator.navigate(
                StudySetDetailScreenDestination(
                    id = it.id,
                    code = ""
                )
            )
        },
        onClassClicked = {
            navigator.navigate(
                ClassDetailScreenDestination(
                    id = it.id,
                    code = it.joinToken ?: "",
                    title = it.title,
                    description = it.description
                )
            )
        },
        onFolderClick = {
            navigator.navigate(
                FolderDetailScreenDestination(
                    id = it.id,
                    code = ""
                )
            )
        },
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
        },
        onSearchStudySetBySubject = { subject ->
            navigator.navigate(
                SearchStudySetBySubjectScreenDestination(
                    id = subject.id,
                    studySetCount = subject.studySetCount,
                    icon = subject.iconRes ?: R.drawable.ic_all
                )
            )
        },
        onClickToCreateStudySet = {
            navigator.navigate(CreateStudySetScreenDestination())
        },
        onHomeRefresh = {
            viewModel.onEvent(HomeUiAction.RefreshHome)
        }
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
private fun Home(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onHomeRefresh: () -> Unit = {},
    subjects: List<SubjectModel> = emptyList(),
    studySets: List<GetStudySetResponseModel> = emptyList(),
    folders: List<GetFolderResponseModel> = emptyList(),
    classes: List<GetClassByOwnerResponseModel> = emptyList(),
    onClassClicked: (GetClassByOwnerResponseModel) -> Unit = {},
    onStudySetClick: (GetStudySetResponseModel) -> Unit = {},
    onFolderClick: (GetFolderResponseModel) -> Unit = {},
    streakCount: Int = 0,
    streakDates: List<LocalDate> = emptyList(),
    currentDate: LocalDate = LocalDate.now(),
    notificationCount: Int = 0,
    onNavigateToSearch: () -> Unit = {},
    onNotificationEnabled: (Boolean) -> Unit = {},
    onClickToCreateStudySet: () -> Unit = {},
    customer: CustomerInfo? = null,
    onCustomerInfoChanged: (CustomerInfo) -> Unit = {},
    onNotificationClicked: (String) -> Unit = {},
    notifications: List<GetNotificationResponseModel> = emptyList(),
    onSearchStudySetBySubject: (SubjectModel) -> Unit = {},
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

    val refreshState = rememberPullToRefreshState()

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
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            state = refreshState,
            isRefreshing = isLoading,
            onRefresh = {
                onHomeRefresh()
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(vertical = 16.dp)
            ) {
                if (studySets.isEmpty() && classes.isEmpty()) {
                    item {
                        Text(
                            text = "Here's how to get started",
                            style = typography.titleLarge.copy(
                                color = colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Card(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .padding(horizontal = 16.dp),
                            onClick = onClickToCreateStudySet,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            )
                        ) {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_three_cards),
                                    contentDescription = "Create a flashcard",
                                    tint = Color.Blue,
                                    modifier = Modifier
                                        .size(50.dp)
                                )

                                Text(
                                    text = "Create your own flashcards",
                                    style = typography.titleMedium.copy(
                                        color = colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .padding(vertical = 10.dp)
                                        .padding(start = 10.dp)
                                        .weight(1f)
                                )
                            }
                        }
                    }
                }
                if (studySets.isNotEmpty()) {
                    item {
                        Text(
                            text = "Study sets",
                            style = typography.titleLarge.copy(
                                color = colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    if (studySets.size == 1) {
                        item {
                            Column {
                                studySets.forEach { studySet ->
                                    StudySetItem(
                                        studySet = studySet,
                                        onStudySetClick = { onStudySetClick(studySet) }
                                    )
                                }
                            }
                        }
                    } else {
                        item {
                            LazyRow(modifier = Modifier.fillMaxWidth()) {
                                items(studySets) { studySet ->
                                    StudySetHomeItem(
                                        studySet = studySet,
                                        onStudySetClick = { onStudySetClick(studySet) }
                                    )
                                }
                            }
                        }
                    }
                }

                if (folders.isNotEmpty()) {
                    item {
                        Text(
                            text = "Folders",
                            style = typography.titleLarge.copy(
                                color = colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                    }

                    if (folders.size == 1) {
                        item {
                            Column {
                                folders.forEach { folder ->
                                    FolderItem(
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        title = folder.title,
                                        numOfStudySets = folder.studySetCount,
                                        onClick = { onFolderClick(folder) },
                                        userResponseModel = folder.owner,
                                    )
                                }
                            }
                        }
                    } else {
                        item {
                            LazyRow(modifier = Modifier.fillMaxWidth()) {
                                items(folders) { folder ->
                                    FolderHomeItem(
                                        title = folder.title,
                                        numOfStudySets = folder.studySetCount,
                                        onClick = { onFolderClick(folder) },
                                        userResponseModel = folder.owner,
                                    )
                                }
                            }
                        }
                    }
                }

                if (classes.isNotEmpty()) {
                    item {
                        Text(
                            text = "Classes",
                            style = typography.titleLarge.copy(
                                color = colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                    }

                    if (classes.size == 1) {
                        item {
                            Column {
                                classes.forEach { classItem ->
                                    ClassItem(
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        classItem = classItem,
                                        onClick = { onClassClicked(classItem) }
                                    )
                                }
                            }
                        }
                    } else {
                        item {
                            LazyRow(modifier = Modifier.fillMaxWidth()) {
                                items(classes) { classItem ->
                                    ClassHomeItem(
                                        classItem = classItem,
                                        onClick = { onClassClicked(classItem) }
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Top 5 subjects have study sets",
                        style = typography.titleLarge.copy(
                            color = colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 16.dp, top = 24.dp)
                    )
                }
                items(subjects, key = { it.id }) { subject ->
                    SubjectItem(
                        subject = subject,
                        onSearchStudySetBySubject = {
                            onSearchStudySetBySubject(subject)
                        },
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(60.dp))
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
    if (showStreakBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showStreakBottomSheet = false
            },
            sheetState = streakBottomSheet,
            dragHandle = {}
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.65f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                )
                Text(
                    text = streakCount.toString(),
                    style = typography.titleLarge.copy(
                        color = streakTitleColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 52.sp
                    )
                )
                Text(
                    text = when (streakCount) {
                        1 -> stringResource(R.string.txt_day_streak)
                        else -> stringResource(R.string.txt_days_streak)
                    },
                    style = typography.titleLarge.copy(
                        color = streakTextColor,
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
            subjects = listOf(
                SubjectModel(
                    1,
                    "All",
                    iconRes = R.drawable.ic_all,
                    color = Color(0xFF7f60f9),
                    studySetCount = 1
                ),
            )
        )
    }
}
