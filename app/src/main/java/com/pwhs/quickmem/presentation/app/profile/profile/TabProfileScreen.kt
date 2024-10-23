package com.pwhs.quickmem.presentation.app.profile.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.R
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.domain.model.status.StatusModel
import com.pwhs.quickmem.domain.model.status.getSelectedStatusId
import com.pwhs.quickmem.domain.model.status.getUserName
import com.pwhs.quickmem.domain.model.status.saveSelectedStatusId
import com.pwhs.quickmem.domain.model.status.saveUserName
import com.pwhs.quickmem.presentation.app.profile.ProfileViewModel
import com.pwhs.quickmem.presentation.app.profile.component.ProfileTextField
import com.pwhs.quickmem.presentation.app.profile.component.StatusBottomSheet
import com.pwhs.quickmem.presentation.app.profile.component.StatusInputField

@Composable
fun TabProfileScreen(
    modifier: Modifier = Modifier ,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ProfileTab(viewModel = viewModel, context = LocalContext.current)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTab(
    viewModel: ProfileViewModel,
    context: Context
) {
    val email by viewModel.emailState.collectAsState()
    var name by remember { mutableStateOf(getUserName(context) ?: "") }
    var inputName by remember { mutableStateOf(name) }
    var selectedStatusId by remember { mutableStateOf(getSelectedStatusId(context)) }
    val status = StatusModel.defaultStatuses.first { it.id == selectedStatusId }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileTextField(
                title = "Name",
                value = inputName,
                placeholder = "Enter your name",
                onValueChange = { newValue ->
                    inputName = newValue
                    saveUserName(context, newValue)
                },
                valueError = if (inputName.isEmpty()) "Name cannot be empty" else ""
            )

            ProfileTextField(
                title = "Email",
                value = email,
                placeholder = "Enter your email",
                onValueChange = { /* Add edit email functionality */ },
                valueError = if (email.isEmpty()) "Email cannot be empty" else ""
            )

            Box(
                modifier = Modifier
                    .align(Alignment.Start)
                    .height(38.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(25.dp))
                    .clip(RoundedCornerShape(25.dp))
                    .clickable { /* Add resend email functionality */ }
                    .padding(8.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = "Resend email",
                    color = Color.Black,
                    modifier = Modifier.padding(2.dp)
                )
            }

            StatusInputField(
                statusModel = status,
                onShowBottomSheet = { showBottomSheet = true }
            )

            Box(
                modifier = Modifier
                    .align(Alignment.Start)
                    .height(38.dp)
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(25.dp))
                    .clip(RoundedCornerShape(25.dp))
                    .clickable { /* Add edit education functionality */ }
                    .padding(8.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = "Edit education information",
                    color = Color.Black,
                    modifier = Modifier.padding(2.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small)
                    .clip(MaterialTheme.shapes.small)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ratings),
                        contentDescription = null,
                        modifier = Modifier
                            .width(250.dp)
                            .height(130.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .align(Alignment.CenterHorizontally)
                    )

                    Text("Study together", style = MaterialTheme.typography.titleMedium)
                    Text("Friends referred: 0", style = MaterialTheme.typography.bodySmall)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp)
                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(25.dp))
                            .clip(RoundedCornerShape(25.dp))
                            .clickable { /* Add share functionality */ }
                            .background(Color.White)
                    ) {
                        Text(
                            text = "Share",
                            color = Color.Black,
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

    StatusBottomSheet(
        showBottomSheet = showBottomSheet,
        sheetState = sheetState,
        statuses = StatusModel.defaultStatuses,
        onStatusSelected = { selectedStatus ->
            selectedStatusId = selectedStatus.id
            saveSelectedStatusId(context, selectedStatus.id)
            showBottomSheet = false
        },
        onDismissRequest = {
            showBottomSheet = false
        }
    )
}





@Preview(showBackground = true)
@Composable
fun TabProfileScreenPreview() {
    TabProfileScreen()
}
