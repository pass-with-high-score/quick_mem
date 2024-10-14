package com.pwhs.quickmem.presentation.auth.signup.email.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pwhs.quickmem.core.data.UserRole
import com.pwhs.quickmem.util.upperCaseFirstLetter

@Composable
fun RadioGroup(
    modifier: Modifier = Modifier,
    onRoleChanged: (UserRole) -> Unit = {}
) {
    val options = listOf(UserRole.TEACHER, UserRole.STUDENT)
    val selectedOption = rememberSaveable { mutableStateOf(options[0]) }
    Column(
        modifier = modifier.padding(vertical = 16.dp),
    ) {
        Text(
            text = "Are you a teacher or a student?",
            style = MaterialTheme.typography.bodyLarge,
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedOption.value == option,
                        onClick = {
                            selectedOption.value = option
                            onRoleChanged(option)
                        }
                    )
                    Text(
                        text = option.name.upperCaseFirstLetter(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}