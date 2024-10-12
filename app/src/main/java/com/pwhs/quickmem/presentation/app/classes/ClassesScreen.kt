package com.pwhs.quickmem.presentation.app.classes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.annotation.parameters.DeepLink

@Composable
@Destination<RootGraph>(deepLinks = [DeepLink(uriPattern = "quickmem://join/class?code={code}")])
fun ClassesScreen(
    modifier: Modifier = Modifier,
    code: String = ""
) {
    Scaffold { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            Text(text = "Classes Screen")
            Text(text = "Code: $code")
        }
    }
}