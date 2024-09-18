plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.pwhs.quickmem"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pwhs.quickmem"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // Compose
    implementation(libs.bundles.compose)
    implementation(platform(libs.androidx.compose.bom))
    // Serialization
    implementation(libs.bundles.serialization)
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.ui.text.google.fonts)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.work)
    // Ktor
    implementation(libs.bundles.ktor)
    // Coil
    implementation(libs.coil.kt.coil.compose)
    // Compose Destination
    implementation(libs.accompanist.flowlayout)
    implementation(libs.compose.destination.core)
    ksp(libs.compose.destination.ksp)
    // WorkManager
    implementation(libs.androidx.work.runtime)
    // Timber
    implementation(libs.jakewharton.timber)
    // Retrofit
    implementation(libs.bundles.retrofit)
    // Easy validator
    implementation(libs.easyvalidation.core)
    // DataStore
    implementation(libs.bundles.datastore)
    // Unit Test
    testImplementation(libs.bundles.testing)
    // Android Test
    androidTestImplementation(libs.bundles.android.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    // Debug Test
    debugImplementation(libs.bundles.debugging)
}