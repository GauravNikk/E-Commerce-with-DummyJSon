plugins {
    alias(libs.plugins.android.library)  // Plugin for Android library
    alias(libs.plugins.kotlin.android)   // Kotlin Android plugin
}

android {
    namespace = "gaurav.iosdialog"  // Your library's package namespace
    compileSdk = 34  // Target SDK

    defaultConfig {
        minSdk = 24  // Minimum SDK version your library supports

        // Test runner
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Proguard configuration for consumer apps using your library
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false  // Disable code shrinking for release builds
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),  // Default Proguard file
                "proguard-rules.pro"  // Custom Proguard rules (if needed)
            )
        }
    }

    compileOptions {
        // Java compatibility settings
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        // JVM target for Kotlin
        jvmTarget = "1.8"
    }
}

dependencies {
    // AndroidX and Material dependencies pulled from version catalogs
    implementation(libs.androidx.core.ktx)         // Core KTX for AndroidX
    implementation(libs.androidx.appcompat)        // AppCompat for backward compatibility
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)                 // Material Components for UI

    // Unit testing dependencies
    testImplementation(libs.junit)                // JUnit for unit tests

    // Instrumentation and UI testing dependencies
    androidTestImplementation(libs.androidx.junit)    // AndroidX JUnit for instrumentation tests
    androidTestImplementation(libs.androidx.espresso.core)  // Espresso for UI testing
}
