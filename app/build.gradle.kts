plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")   // ✅ IMPORTANT
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.studchats"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.studchats"
        minSdk = 29
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
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Firebase
    implementation(libs.firebase.database)
    implementation("com.google.firebase:firebase-database-ktx:20.3.0")

    // ✅ ROOM DATABASE (CORRECT)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.material3)
    kapt("androidx.room:room-compiler:2.6.1")

    // Optional (recommended for Kotlin + coroutines)
    implementation("androidx.room:room-ktx:2.6.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}


