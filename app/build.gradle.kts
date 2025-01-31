plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.faqiy.githubuser"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.faqiy.githubuser"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String","API_URL","\"https://api.github.com/\"")
        buildConfigField("String","TOKEN","\"token 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\"")
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
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.2.0")
    kapt ("androidx.lifecycle:lifecycle-compiler:2.2.0")

    //ktx
    implementation ("androidx.fragment:fragment-ktx:1.7.1")

    // Gson
    implementation("io.coil-kt:coil:2.6.0")
    implementation ("com.google.code.gson:gson:2.10.1")

    // retrofit : mempermudah proses pertukaran data antara aplikasi android dengan server melalui REST API
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.2")

    // corountines : Asingkronus
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    // ROOM : Menyimpan data di database lokal
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-rxjava3:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

    // DataStore : Menyimpan data secara asinkron
    implementation ("androidx.datastore:datastore-preferences-core:1.1.1")
    implementation ("androidx.datastore:datastore-preferences:1.1.1")
}