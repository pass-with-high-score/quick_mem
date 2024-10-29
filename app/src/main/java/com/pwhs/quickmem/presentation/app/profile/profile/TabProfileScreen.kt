package com.pwhs.quickmem.presentation.app.profile.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.pwhs.quickmem.presentation.app.profile.component.SettingsButton
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.request.ImageRequest
import com.pwhs.quickmem.R

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun TabProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,

) {
    val name by viewModel.nameState.collectAsState(initial = "")
    val avatarUrl by viewModel.avatarUrlState.collectAsState(initial = "")
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.updateAvatar(it.toString())
        } ?: Toast.makeText(context, "Không thể chọn ảnh", Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {},
        content = { paddingValues ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ProfileTab(
                    name = name,
                    avatarUrl = avatarUrl,
                    navigator = navigator,
                    onImageClick = {
                        launcher.launch("image/*")
                    }
                )
            }
        }
    )
}

@Composable
fun ProfileTab(
    name: String,
    avatarUrl: String,
    navigator: DestinationsNavigator,
    onImageClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avatarUrl.ifEmpty { null })
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .build(),
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable { onImageClick() },
            contentScale = ContentScale.Crop
        )

        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingsButton(
            text = "Cài đặt của bạn",
            onClick = { /* Hành động khi nhấn vào cài đặt */ }
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}





