package com.pwhs.quickmem.presentation.app.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pwhs.quickmem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAccountBottomSheet(
    onDeleteConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onCancel, modifier = Modifier.align(Alignment.End)) {
            Icon(Icons.Default.Close, contentDescription = "Close")
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_sad_cat),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("We're sad to see you go!", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Are you sure you don't want to reconsider?\nDid we do something wrong?",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onDeleteConfirm,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7272E)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("DELETE ACCOUNT", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onCancel,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("CANCEL")
        }
    }
}

