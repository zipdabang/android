buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
        // for serialization
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.6.10")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0-rc01" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.47" apply false
}

