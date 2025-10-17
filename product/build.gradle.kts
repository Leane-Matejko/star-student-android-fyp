// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.3.0" apply false
    kotlin("android") version "1.9.0" apply false
    // alias(libs.plugins.android.application)
    // alias(libs.plugins.kotlin.android)
    // alias(libs.plugins.kotlin.compose)
}

buildscript {
    dependencies {
        classpath("com.github.triplet.gradle:play-publisher:3.8.4")
    }
}



