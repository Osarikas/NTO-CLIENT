plugins {
    kotlinAndroid
    androidApplication
    jetbrainsKotlinSerialization version Version.Kotlin.language
    kotlinAnnotationProcessor
    id("com.google.dagger.hilt.android") version("2.51.1")
}

val packageName = "ru.myitschool.work"

android {
    namespace = packageName
    compileSdk = 35

    defaultConfig {
        applicationId = packageName
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = Version.Kotlin.javaSource
        targetCompatibility = Version.Kotlin.javaSource
    }

    kotlinOptions {
        jvmTarget = Version.Kotlin.jvmTarget
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity-ktx:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    defaultLibrary()

    implementation(Dependencies.AndroidX.activity)
    implementation(Dependencies.AndroidX.fragment)
    implementation(Dependencies.AndroidX.constraintLayout)

    implementation("androidx.navigation:navigation-fragment-ktx:2.8.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.7")

    implementation(Dependencies.Retrofit.library)
    implementation(Dependencies.Retrofit.gsonConverter)
    implementation("io.ktor:ktor-client-content-negotiation:3.0.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.3")
    implementation("io.ktor:ktor-client-cio:3.0.3")
    implementation("io.ktor:ktor-client-core:3.0.3")
    implementation("io.ktor:ktor-client-serialization:2.3.7")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("androidx.datastore:datastore-preferences:1.1.2")
    implementation("com.google.mlkit:barcode-scanning:17.3.0")

    val cameraX = "1.4.1"
    implementation("androidx.camera:camera-core:$cameraX")
    implementation("androidx.camera:camera-camera2:$cameraX")
    implementation("androidx.camera:camera-lifecycle:$cameraX")
    implementation("androidx.camera:camera-view:$cameraX")
    implementation("androidx.camera:camera-mlkit-vision:1.4.1")

    val hilt = "2.51.1"
    implementation("com.google.dagger:hilt-android:$hilt")
    kapt("com.google.dagger:hilt-android-compiler:$hilt")
}

kapt {
    correctErrorTypes = true
}
