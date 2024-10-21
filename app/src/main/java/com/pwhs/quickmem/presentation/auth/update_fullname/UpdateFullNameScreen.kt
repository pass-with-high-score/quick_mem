package com.pwhs.quickmem.presentation.auth.update_fullname

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.util.gradientBackground
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun UpdateFullNameScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateFullNameViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    // Lắng nghe sự kiện UI từ ViewModel
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UpdateFullNameUIEvent.UpdateSuccess -> {
                    viewModel.onEvent(UpdateFullNameUIAction.Submit)
                    Toast.makeText(
                        context,
                        "Update Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is UpdateFullNameUIEvent.ShowError -> {
                    Toast.makeText(
                        context,
                        "Update failure",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    UpdateFullNameUI(
        modifier,
        fullName = uiState.fullName,
        onNameChanged = { name ->
            viewModel.onEvent(UpdateFullNameUIAction.FullNameChanged(name))
        },
        onSubmitClick = {
            viewModel.onEvent(UpdateFullNameUIAction.Submit)
            navigator.navigate(HomeScreenDestination) {
                popUpTo(HomeScreenDestination) {
                    inclusive = true
                    launchSingleTop = true
                }
            }
        }
    )
}

@Composable
fun UpdateFullNameUI(
    modifier: Modifier = Modifier,
    fullName: String = "",
    onNameChanged: (String) -> Unit = {},
    onSubmitClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf(fullName) }

    Scaffold(
        modifier = modifier.gradientBackground(),
        containerColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFB3B8FF),
                    modifier = Modifier.size(64.dp)
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFB3B8FF),
                    modifier = Modifier.size(64.dp)
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFB3B8FF),
                    modifier = Modifier.size(64.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .border(
                        width = 2.dp,
                        color = Color(0xFF454ADE),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(
                        color = Color(0xFF454ADE),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = "Let's get started... How can I call you?",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFEBEBFF)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { newName ->
                    name = newName
                    onNameChanged(newName)
                },
                placeholder = {
                    Text(
                        text = "Your first name...",
                        color = Color(0xFFA8ADFC),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(
                        Color(0xFF5E5CE6),
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = Color(0xFF454ADE),
                        shape = RoundedCornerShape(12.dp)
                    ),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(120.dp))

            Button(
                onClick = {
                    onSubmitClick()

                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF696969),
                    contentColor = Color(0xFFEBEBFF)
                )
            ) {
                Text(text = "Continue")
            }
        }
    }
}

@Preview
@Composable
fun PreviewSetName() {
    UpdateFullNameUI()
}


