// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("com.google.gms.google-services") version "4.3.8" apply false
    id("androidx.navigation.safeargs") version "2.7.7" apply false
}
buildscript {
    repositories {
        google()
    }
    dependencies {
         // Update with latest version if needed
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}
