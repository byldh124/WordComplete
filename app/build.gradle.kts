import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
}

val properties = gradleLocalProperties(rootDir)

android {
    namespace = "com.moondroid.wordcomplete"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.moondroid.wordcomplete"
        minSdk = 21
        targetSdk = 33
        versionCode = 13
        versionName = "1.2.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val googleAdsAppId: String = properties.getProperty("google.ads.app.id")


        manifestPlaceholders["googleAdsAppId"] = googleAdsAppId

    }

    buildTypes {
        debug {
            resValue("string", "banner_id", "ca-app-pub-3940256099942544/6300978111")
            resValue("string", "foreground_id", "ca-app-pub-3940256099942544/1033173712")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = false

            @Suppress("UnstableApiUsage")
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

    @Suppress("UnstableApiUsage")
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.google.android.gms:play-services-ads:22.1.0")

    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.5.7")

    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.5.3")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // okhttp3
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

}