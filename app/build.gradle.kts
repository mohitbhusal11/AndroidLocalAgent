plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.localai.agent"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.localai.agent"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
        compose = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":core-network"))
    implementation(project(":core-ai"))
    implementation(project(":core-database"))
    implementation(project(":core-agent"))
    implementation(project(":core-memory"))
    implementation(project(":core-voice"))
    implementation(project(":core-permissions"))
    implementation(project(":feature-chat"))
    implementation(project(":feature-voice"))
    implementation(project(":feature-memory"))
    implementation(project(":feature-notifications"))
    implementation(project(":feature-automation"))
    implementation(project(":feature-accessibility"))
    implementation(project(":feature-research"))
    implementation(project(":feature-settings"))
    implementation(project(":feature-shortcuts"))
    implementation(project(":feature-productivity"))
    implementation(project(":feature-doomscroll"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.hilt.work)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
