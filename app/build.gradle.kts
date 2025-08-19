plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.devicetemperature"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.devicetemperature"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
}

dependencies {
    // AppCompat for Activity, UI helpers
    implementation("androidx.appcompat:appcompat:1.7.0")

    // Material Design UI components
    implementation("com.google.android.material:material:1.12.0")

    // ConstraintLayout (if you’re using constraint-based layouts)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Core KTX (handy Kotlin extensions)
    implementation("androidx.core:core-ktx:1.13.1")

    // Optional: Lifecycle support (good for services/activities)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
