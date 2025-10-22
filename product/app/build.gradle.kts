plugins {
    id("com.github.triplet.play")version "3.8.4"
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    
}    

play {
    serviceAccountCredentials.set(file("starstudent-76c6c19da849.json"))
    track.set("pre-alpha")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
   
            storeFile = file("release-keystore.jks") //keystore
            storePassword = "password"
            keyAlias = "alias"
            keyPassword = "password"

        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
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

dependencies {

    // Compose BOM for version alignment
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))

    // Compose UI and Material3 libraries (version managed by BOM)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.core:core-ktx:1.12.1")
    implementation("com.google.accompanist:accompanist-insets:0.30.1")

    // Compose Compiler (explicit version)
    implementation("androidx.compose.compiler:compiler:1.5.0")

    // Lifecycle runtime with Kotlin extensions
    implementation("androidx.lifecycle:lifecycle-runtime-ktx")

    // Activity Compose integration
    implementation("androidx.activity:activity-compose")

    // Unit testing with JUnit 4
    testImplementation("junit:junit:4.13.2")

    // Android instrumented tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Compose UI testing (JUnit4)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Debugging tools for Compose UI
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}