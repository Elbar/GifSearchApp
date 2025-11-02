plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.sample.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)

    implementation(libs.androidx.recyclerview)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.koin.android)
    implementation(libs.androidx.runtime)
    implementation(libs.koin.android.compose)

    // JUnit
    testImplementation(libs.junit)

    // Mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)

    // Coroutines test
    testImplementation(libs.kotlinx.coroutines.test)

    // Espresso
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.espresso.intents)

    // Koin Test
    testImplementation(libs.koin.test)
    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.koin.test.junit4)

    // Image loading
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.paging.compose)

    // Material
    implementation(libs.compose.material3)
    implementation(libs.material)

    implementation(libs.navigation.compose)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.turbine)
    implementation(libs.androidx.material.icons.extended.android)

}