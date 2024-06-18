plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.llyodsassignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.llyodsassignment"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "BASE_URL", "\"https://api.thecatapi.com/\"")
        buildConfigField("String", "API_KEY", "\"live_kNTyWCuImXvvN2V7KcjmNdsE1MMSzQZFgZgYNpFrPjrnlOM6TuuRyT8aOjmjs7bD\"")
    }
    kapt {
        correctErrorTypes = true
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(project(":app"))
    androidTestImplementation(project(":app"))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //di-hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    //hilt-jetpack
    implementation(libs.androidx.hilt.navigation.compose)
    //coroutine
    implementation(libs.androidx.coroutine)
    implementation(libs.androidx.coroutine.core)
    //okhttp
    implementation(libs.androidx.okhttp)
    //retrofit
    implementation(libs.androidx.retrofit)
    implementation(libs.androidx.retrofit.coroutine)
    //retrofit-moshi
    implementation(libs.converter.moshi)
    //moshi
    implementation(libs.moshi.kotlin)
    //coroutine-test
    testImplementation(libs.kotlinx.coroutines.test)
    //mockk-testing
    testImplementation(libs.mockk)
    //mockito-testing
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.mockito.android)
    //mock webserver
    androidTestImplementation(libs.mockwebserver)
    //turbine-for-test-flows
    testImplementation(libs.turbine)
    androidTestImplementation(libs.turbine)
    //room-db
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    //nav-graph
    implementation(libs.androidx.navigation.compose)
    //coil- img loading
    implementation(libs.coil.compose)
    //arch-core-testing
    implementation(libs.androidx.core.testing)
}