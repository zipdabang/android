import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id ("com.google.dagger.hilt.android")
    // kapt 사용 목적
    id ("kotlin-kapt")
    // serialization for using datastore
    id ("org.jetbrains.kotlin.plugin.serialization")
}

val properties = Properties()
val localPropertiesFile = File("local.properties")

if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { properties.load(it) }
}
//localPropertiesFile.inputStream().use { properties.load(it) }

android {
    namespace = "com.zipdabang.zipdabang_android"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.zipdabang.zipdabang_android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val kakaoNativeAppKey = properties.getProperty("kakao_native_app_key")
        val kakaoOauthHost = properties.getProperty("kakao_oauth_host")
        val googleWebClientId = properties.getProperty("google_web_client_id")
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", kakaoNativeAppKey)
        resValue("string", "kakao_oauth_host", kakaoOauthHost)
        buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", googleWebClientId)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.firebase:firebase-auth-ktx:22.1.0")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.7")
    implementation("androidx.navigation:navigation-runtime-ktx:2.6.0")
    implementation("com.google.firebase:firebase-messaging-ktx:23.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // firebase
    // for auth
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation("com.google.android.gms:play-services-auth:20.6.0")

    // for cloud messaging


    // kakao login
    implementation ("com.kakao.sdk:v2-all:2.15.0") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation ("com.kakao.sdk:v2-user:2.15.0") // 카카오 로그인
    
    //for Image Loading using coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    //for material
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.1")
    implementation ("androidx.compose.material:material:1.2.0")

    //for viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    //for navigation
    implementation("androidx.navigation:navigation-compose:2.6.0")


    // dagger-hilt for DI
    implementation ("com.google.dagger:hilt-android:2.47")
    kapt ("com.google.dagger:hilt-compiler:2.47")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("androidx.navigation:navigation-runtime:2.6.0")

    // preferences datastore
    implementation ("androidx.datastore:datastore:1.0.0")
    // if lists should be saved in proto datastore
    implementation ("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
}

kapt {
    correctErrorTypes = true

}