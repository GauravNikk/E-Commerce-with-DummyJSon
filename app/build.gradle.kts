plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.gaurav.testapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gaurav.testapp"
        minSdk = 24
        targetSdk = 34
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":iosdialog"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(libs.shimmer)
    implementation(libs.retrofit)
    implementation(libs.coroutines)
    implementation(libs.coil)
    implementation(libs.kodein)
    implementation(libs.livedata)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0"
    //implementation "androidx.core:core-ktx:1.7.0"
    //implementation "org.kodein.di:kodein-di:7.10.0"
    //implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0"
    //implementation "io.coil-kt:coil:2.0.0"
    //implementation "com.squareup.retrofit2:retrofit:2.9.0"
    //implementation "com.squareup.retrofit2:converter-gson:2.9.0"
}