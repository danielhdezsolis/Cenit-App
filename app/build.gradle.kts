import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android) // Aplicamos el plugin de Hilt
    alias(libs.plugins.kotlinx.serialization) // Agregar el plugin de serialization
    id("kotlin-kapt") // Necesario para KAPT
}

val properties = Properties().apply {
    file("../local.properties").inputStream().use { load(it) }
}

val supabaseKey: String = properties.getProperty("supabaseKey")
val supabaseUrl: String = properties.getProperty("supabaseUrl")

android {
    namespace = "com.example.cenit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cenit"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "supabaseUrl", "\"$supabaseUrl\"")
        buildConfigField("String", "supabaseKey", "\"$supabaseKey\"")
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
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get() // Usar la versión de Compose definida
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.material3.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Navigation dependencies
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    // Hilt dependencies
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler) // Para la compilación de anotaciones
    // Jetpack Compose dependencies
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.navigation.compose)

    // Para soportar las previews
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.supabase.gotrue)        // Supabase GoTrue
    implementation(libs.ktor.client.cio)        // Ktor Client CIO
    implementation(libs.lifecycle.viewmodel.compose) // Lifecycle ViewModel Compose

}