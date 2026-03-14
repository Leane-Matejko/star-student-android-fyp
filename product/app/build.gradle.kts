import org.gradle.testing.jacoco.tasks.JacocoReport

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    kotlin("kapt")
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

kapt{
    arguments {
        arg("room.schemaLocation","projectDir/schemas")
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
    implementation(libs.androidx.navigation.testing)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(platform("com.google.firebase:firebase-bom:34.6.0")){
//        exclude(group = "com.google.android.gms")
    }
//    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-functions")

    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    testImplementation("androidx.compose.ui:ui-test-junit4:1.9.5")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$version")

    debugImplementation ("androidx.compose.ui:ui-test-manifest:$rootProject.composeVersion")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    implementation("androidx.fragment:fragment-ktx:1.6.2")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    androidTestImplementation("org.jetbrains.kotlin:kotlin-test")


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

//fun Dependency.exclude(group: String) {}
