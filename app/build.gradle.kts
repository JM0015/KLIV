plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.kliv"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.kliv"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            /*숨겨놓은 API KEY 값 안전하게 가져오기*/
            getByName("debug") {
                buildConfigField("String", "GOOGLE_MAPS_PLACES_API_KEY", "\"${findProperty("GOOGLE_MAPS_PLACES_API_KEY") ?: ""}\"")
            }
        }

        release {
            /*숨겨놓은 API KEY 값 안전하게 가져오기*/
            getByName("release") {
                buildConfigField("String", "GOOGLE_MAPS_PLACES_API_KEY", "\"${findProperty("GOOGLE_MAPS_PLACES_API_KEY") ?: ""}\"")
            }

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
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    /*필요한 라이브러리 설정*/
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-analytics")
}