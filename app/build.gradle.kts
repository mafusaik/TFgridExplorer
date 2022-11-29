plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("androidx.navigation.safeargs.kotlin")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id("com.apollographql.apollo3").version("3.6.2")
}

android {
    compileSdk = 32

    val googleMapKey: String by project

    defaultConfig {
        applicationId = "by.homework.hlazarseni.tfgridexplorer"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders["googleMapKey"] = googleMapKey

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    apollo {
        packageName.set("by.homework")
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    //Koin
    implementation("io.insert-koin:koin-android:3.2.2")

    //Room
    kapt ("androidx.room:room-compiler:2.4.3")
    implementation ("androidx.room:room-runtime:2.4.3")
    implementation ("androidx.room:room-ktx:2.4.3")

    //API
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.apollographql.apollo3:apollo-runtime:3.6.2")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    //LIveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")

    //Coil
    implementation("io.coil-kt:coil:2.2.0")

    // Google Map
    implementation("com.google.maps.android:android-maps-utils:1.3.3")
    implementation("com.google.android.gms:play-services-maps:18.0.2")
    implementation("com.google.android.gms:play-services-location:19.0.1")

    // Paging v3
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")

    //pull-to-refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.5.3")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}