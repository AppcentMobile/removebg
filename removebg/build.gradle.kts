@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    `maven-publish`
}

android {
    namespace = "dev.eren.removebg"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    implementation(libs.core.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.pytorch.android.lite)
    implementation(libs.pytorch.android.torchvision.lite)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

afterEvaluate {
    publishing {
        publications {
            register("mavenJava", MavenPublication::class) {
                from(components["release"])
                groupId = "dev.eren"
                artifactId = "removebg"
                version = "1.0.0"
            }
        }
    }
}