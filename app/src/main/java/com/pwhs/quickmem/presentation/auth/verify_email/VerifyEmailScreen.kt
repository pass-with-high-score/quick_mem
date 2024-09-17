package com.pwhs.quickmem.presentation.auth.verify_email

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Composable
@Destination<RootGraph>
fun VerifyEmailScreen(
    modifier: Modifier = Modifier,
    viewModel: VerifyEmailViewModel = hiltViewModel(),
    email: String = "nguyenquangminh570@gmail.com"
) {
    VerifyEmail(modifier = modifier, email = email)
}

@Composable
private fun VerifyEmail(
    modifier: Modifier = Modifier,
    email: String = "nguyenquangminh570@gmail.com"
) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("Verify Email Screen")
            Text("Email: $email")
        }
    }
}

@Preview
@Composable
fun VerifyEmailScreenPreview() {
    VerifyEmail()
}