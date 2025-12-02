import org.gradle.testing.jacoco.tasks.JacocoReport

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
//    id("com.android.application")
    id("com.google.gms.google-services")
    jacoco
}

android {
    namespace = "com.example.starstudent"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.starstudent"
        minSdk = 24
        targetSdk = 36
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
        debug {
            isTestCoverageEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}


//testing that the google service plugin is correctly configured for the DB firebase connection
tasks.register("checkGoogleServicesPlugin") {
    doLast {
        if (plugins.hasPlugin("com.google.gms.google-services")) {
            println("Google Services plugin is applied.")
        } else {
            println("Google Services plugin is NOT applied.")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(platform("com.google.firebase:firebase-bom:34.6.0"))
    implementation("com.google.firebase:firebase-analytics")

    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation(kotlin("test"))
    testImplementation(libs.junit)

    testImplementation("androidx.compose.ui:ui-test-junit4:1.9.5")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$version")

    debugImplementation ("androidx.compose.ui:ui-test-manifest:$rootProject.composeVersion")


}

val jacocoAndroidTestReport by tasks.registering(JacocoReport::class) {
    // Make sure unit tests run first
    dependsOn("connectedDebugAndroidTest")

    reports {
        html.required.set(true) // HTML report
        xml.required.set(true)  // XML report (for CI tools)
    }

    // compiled classes for coverage
    classDirectories.setFrom(
        fileTree("$buildDir/tmp/kotlin-classes/debug") {
            exclude(
                "**/R.class",
                "**/R$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*"
            )
        }
    )

    // source code for reporting
    sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))

    // Jacoco execution data from androidTest
    executionData.setFrom(fileTree(buildDir) {
        include("outputs/code_coverage/debugAndroidTest/connected/**/*.ec")
    })
}
