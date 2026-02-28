// kk/build.gradle.kts (Project level)
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.services) apply false
    // ✅ Do NOT add kotlin plugin here (Android plugin already handles it)
}