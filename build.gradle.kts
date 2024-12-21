// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.google.crashlytics) apply false
}

tasks.register("buildRelease") {
    group = "distribution"
    description = "Build the app for distribution (release version)"

    dependsOn(":app:assembleRelease") // Build release APK from the app module
}

tasks.register("buildDebug") {
    group = "build"
    description = "Build the app for testing (debug version)"
    dependsOn(":app:assembleDebug") // Build debug APK
}
