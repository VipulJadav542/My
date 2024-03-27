import android.databinding.tool.processing.ViewBindingErrorMessages
import com.android.build.api.dsl.ViewBinding

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {


    namespace = "com.rk.afterstart"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
        compose = true
//        dataBinding = true
    }

    defaultConfig {
        applicationId = "com.rk.afterstart"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-firestore:24.10.3")
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //for custome Toast
    implementation("com.github.emreesen27:Android-Custom-Toast-Message:1.0.5")

    //for lottie animation and GIF
    implementation("com.airbnb.android:lottie:6.3.0")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.28")

    //for custome alert Dialogbox
    implementation("io.github.tutorialsandroid:kalertdialog:20.4.8")
    implementation("com.github.TutorialsAndroid:progressx:v6.0.19")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // for navigation graph
    val nav_version = "2.7.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //for animation bottom  navigation bar
    implementation("nl.joery.animatedbottombar:library:1.1.0")

    //for android animation
    //https://github.com/gayanvoice/android-animations-kotlin
    implementation("com.github.gayanvoice:android-animations-kotlin:1.0.1")

    //for loading animations
    //https://github.com/ybq/Android-SpinKit
    implementation("com.github.ybq:Android-SpinKit:1.4.0")

    //for time range picker
    //https://github.com/Droppers/TimeRangePicker
    implementation("nl.joery.timerangepicker:timerangepicker:1.0.0")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")

    implementation("com.google.android.gms:play-services-auth:21.0.0")

    //for kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //for picasso libraries
    implementation("com.squareup.picasso:picasso:2.71828")

    //for GSON libraries
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.github.IslamKhSh:CardSlider:1.0.1")

    implementation("com.github.bumptech.glide:glide:4.16.0")


    //for retrofit API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // for admob
    // App id=ca-app-pub-3940256099942544~3347511713
    // banner unit id=ca-app-pub-3940256099942544/6300978111
    implementation("com.google.android.gms:play-services-ads:23.0.0")
}
