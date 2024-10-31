package com.pwhs.quickmem.presentation.app.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R

@Composable
fun DeleteAccountButton(
    modifier: Modifier = Modifier,
    onDeleteConfirm: () -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.medium)
            .clickable { showDialog = true }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "Delete account",
                tint = Color.Red,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Delete account",
                color = Color.Red,
                fontSize = 16.sp
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Navigate",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Delete account", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sad_cat),
                        contentDescription = "Sad cat",
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "We're sad to see you go!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Are you sure you don’t want to reconsider?\nDid we do something wrong? ",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onDeleteConfirm()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7272E)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "DELETE ACCOUNT",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color.White, shape = RoundedCornerShape(50))
                ) {
                    Text(
                        text = "CANCEL",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            shape = RoundedCornerShape(16.dp),
        )
    }
}


