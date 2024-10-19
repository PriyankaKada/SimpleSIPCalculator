plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.sipcalculator"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sipcalculator"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler )// For Java
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    // Chart and graph library
    implementation ("com.github.blackfizz:eazegraph:1.2.2@aar")
    implementation ("com.nineoldandroids:library:2.4.0")
    implementation ("com.google.android.material:material:1.8.0")
    androidTestImplementation(libs.ext.junit)

    androidTestImplementation(libs.espresso.core)
}