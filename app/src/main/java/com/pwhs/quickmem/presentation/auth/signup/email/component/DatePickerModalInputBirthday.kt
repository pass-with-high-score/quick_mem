package com.pwhs.quickmem.presentation.auth.signup.email.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pwhs.quickmem.R
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    initialDate: Long? = null
) {
    Timber.d("Initial Date: $initialDate")
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input,
        yearRange = 1930..2024,
        initialSelectedDateMillis = initialDate ?: System.currentTimeMillis()
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(stringResource(R.string.txt_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.txt_cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
