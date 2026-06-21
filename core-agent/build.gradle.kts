plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.localai.agent.coreagent"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
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
    implementation(project(":core"))
    implementation(project(":core-network"))
    implementation(project(":core-database"))
    implementation(project(":core-memory"))
    implementation(project(":core-ai"))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.gson)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
