import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.crashlytics)
}

android {
    namespace = "com.pwhs.quickmem"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.pwhs.quickmem"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        val localProperties = Properties()
        try {
            file(rootProject.file("local.properties")).inputStream()
                .use { localProperties.load(it) }
        } catch (_: Exception) {
            println("local.properties not found, using default values")
        }

        val baseUrl: String = localProperties.getProperty("BASE_URL") ?: "https://api.quickmem.app/"
        val emailVerificationUrl: String = localProperties.getProperty("EMAIL_VERIFICATION_URL")
            ?: "https://checkemail.quickmem.app/v0/check_email"
        val bannerAdsId: String =
            localProperties.getProperty("BANNER_ADS_ID") ?: "ca-app-pub-5725743620724195/1415385680"
        val interstitialAdsId: String = localProperties.getProperty("INTERSTITIAL_ADS_ID")
            ?: "ca-app-pub-5725743620724195/5715891999"
        val rewardAdsId: String =
            localProperties.getProperty("REWARD_ADS_ID") ?: "ca-app-pub-5725743620724195/5188260450"
        val rewardedInterstitialAdsId: String =
            localProperties.getProperty("REWARDED_INTERSTITIAL_ADS_ID")
                ?: "ca-app-pub-5725743620724195/6760705307"
        val oneSignalAppId: String = localProperties.getProperty("ONESIGNAL_APP_ID")
            ?: "b2f7f966-d8cc-11e4-bed1-df8f05be55ba"
        val revenueCatApiKey: String = localProperties.getProperty("REVENUECAT_API_KEY")
            ?: "goog_TBgLrymHTtfZJQzfyRseRIYlPER"

        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        buildConfigField("String", "EMAIL_VERIFICATION_URL", "\"$emailVerificationUrl\"")
        buildConfigField("String", "BANNER_ADS_ID", "\"$bannerAdsId\"")
        buildConfigField("String", "INTERSTITIAL_ADS_ID", "\"$interstitialAdsId\"")
        buildConfigField("String", "REWARD_ADS_ID", "\"$rewardAdsId\"")
        buildConfigField("String", "REWARDED_INTERSTITIAL_ADS_ID", "\"$rewardedInterstitialAdsId\"")
        buildConfigField("String", "ONESIGNAL_APP_ID", "\"$oneSignalAppId\"")
        buildConfigField("String", "REVENUECAT_API_KEY", "\"$revenueCatApiKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    androidResources {
        generateLocaleConfig = true
    }
}

dependencies {
    implementation(libs.purchases)
    implementation(libs.purchases.ui)
    implementation(libs.accompanist.permissions)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.bundles.roomdb)
    ksp(libs.androidx.room.compiler)
    implementation(libs.compose.charts)
    implementation(libs.firebase.crashlytics)

    implementation(libs.play.services.ads)

    implementation(projects.composeCardstack)
    implementation(projects.easycrop)
    implementation(libs.lottie.compose)

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
    // Coil
    implementation(libs.coil.kt.coil.compose)
    // Compose Destination
    implementation(libs.compose.destination.animation.core)
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
    // RevenueCat
    implementation(libs.bundles.revenuecat)
    // Unit Test
    testImplementation(libs.bundles.testing)
    // Android Test
    androidTestImplementation(libs.bundles.android.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    // Debug Test
    debugImplementation(libs.bundles.debugging)

}