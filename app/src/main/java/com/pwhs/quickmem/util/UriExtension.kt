package com.pwhs.quickmem.util

import android.net.Uri
import android.content.Context
import java.io.ByteArrayOutputStream
import java.io.InputStream

fun Uri.toByteArray(context: Context): ByteArray? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(this)
        inputStream?.use { stream ->
            val buffer = ByteArrayOutputStream()
            val data = ByteArray(1024)
            var length: Int
            while (stream.read(data).also { length = it } != -1) {
                buffer.write(data, 0, length)
            }
            buffer.flush()
            buffer.toByteArray()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
