package com.pwhs.quickmem.util

import android.app.LocaleManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.os.LocaleListCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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

fun Context.changeLanguage(languageCode: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList.forLanguageTags(languageCode)
    } else {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }
}

fun Context.getLanguageCode(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSystemService(LocaleManager::class.java).applicationLocales[0]?.toLanguageTag()?.split("-")?.first() ?: "en"
    } else {
        AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag()?.split("-")?.first() ?: "en"
    }
}