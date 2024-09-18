package com.pwhs.quickmem.presentation.auth.verify_email

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pwhs.quickmem.presentation.auth.component.AuthTopAppBar
import com.pwhs.quickmem.presentation.auth.verify_email.components.Otp
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
    email: String = "nguyenquangminh570@gmail.com",
    onNavigationIconClick: () -> Unit = {}
) {
    Scaffold (
        topBar = {
            AuthTopAppBar(
                onClick = onNavigationIconClick,
            )
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
//            Text("Verify Email Screen")
//            Text("Email: $email")
            val successColor = Color(0xff17917a)
            val errorColor = Color(0xFFFF6969)

            var error by remember {
                mutableStateOf(false)
            }
            var success by remember {
                mutableStateOf(false)
            }

            val bgColor by animateColorAsState((if (success) successColor else if (error) errorColor else Color.White).copy(alpha = .2f))

            Box(modifier=Modifier.background(bgColor), contentAlignment = Alignment.Center){
                Otp(
                    count = 5,
                    error = error,
                    success = success,
                    errorColor = errorColor,
                    successColor = successColor,
                    focusedColor = Color(0xff313131),
                    unFocusedColor = Color.Gray,
                    onFinish = { otp->
                        if(otp == "12345"){
                            success = true
                            error = false
                        }else{
                            success = false
                            error = true
                        }
                    },
                    modifier=Modifier.size(60.dp,90.dp),
                )
            }
        }
    }
}


@Preview
@Composable
fun VerifyEmailScreenPreview() {
    VerifyEmail()
}