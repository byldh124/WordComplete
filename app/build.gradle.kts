import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val properties = gradleLocalProperties(rootDir)

android {
    namespace = "com.moondroid.wordcomplete"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moondroid.wordcomplete"
        minSdk = 23
        targetSdk = 34
        versionCode = 23
        versionName = "1.2.12"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val googleAdsAppId: String = properties.getProperty("google.ads.app.id")
        manifestPlaceholders["googleAdsAppId"] = googleAdsAppId

        val slackToken = properties.getProperty("slack.token")
        resValue("string", "slack_token", slackToken)
    }

    buildTypes {
        debug {
            resValue("string", "banner_id", "ca-app-pub-3940256099942544/6300978111")
            resValue("string", "foreground_id", "ca-app-pub-3940256099942544/1033173712")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            val bannerId: String = properties.getProperty("google.ads.banner.id")
            val foregroundId: String = properties.getProperty("google.ads.foreground.id")

            resValue("string", "banner_id", bannerId)
            resValue("string", "foreground_id", foregroundId)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        viewBinding = true
        //noinspection DataBindingWithoutKapt
        dataBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.google.android.gms:play-services-ads:22.3.0")

    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.6.1")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.2")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.7.2")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    // dagger hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")

    // okhttp3
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    implementation(platform("com.google.firebase:firebase-bom:32.1.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // lottie for animation
    implementation ("com.airbnb.android:lottie:6.3.0")

}

kapt {
    correctErrorTypes = true
}