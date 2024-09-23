package com.pwhs.quickmem.util

import androidx.compose.runtime.Composable

@Composable
fun Int.each(block: @Composable (Int) -> Unit) {
    for (i in 0 until this) {
        block(i)
    }
}