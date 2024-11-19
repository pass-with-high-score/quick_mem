package com.pwhs.quickmem.util

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

fun Context.bitmapToUri(bitmap: ImageBitmap): Uri {
    val file = File(this.cacheDir, "image_${System.currentTimeMillis()}.png")
    return try {
        // Save the bitmap to the file
        val outputStream = FileOutputStream(file)
        bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        // Return the Uri for the file
        Uri.fromFile(file)
    } catch (e: IOException) {
        e.printStackTrace()
        Uri.EMPTY
    }
}

fun Context.uriToBitmap(uri: Uri): Bitmap {
    return try {
        val inputStream = this.contentResolver.openInputStream(uri)
        Bitmap.createBitmap(BitmapFactory.decodeStream(inputStream))
    } catch (e: IOException) {
        e.printStackTrace()
        Bitmap.createBitmap(0, 0, Bitmap.Config.ARGB_8888)
    }
}

fun Context.updateLocale(languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.displayMetrics)
}

fun Context.changeAppIcon(aliasToEnable: String) {
    val pm = this.packageManager
    val aliases = listOf("$packageName.MainActivity", "$packageName.MainActivityAlias")

    aliases.forEach { alias ->
        val state = if (alias == aliasToEnable)
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        else
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED

        pm.setComponentEnabledSetting(
            ComponentName(this, alias),
            state,
            PackageManager.DONT_KILL_APP
        )
    }
}