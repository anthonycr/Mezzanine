plugins {
    alias(libs.plugins.android.app)
    id("com.anthonycr.mezzanine.plugin")
    kotlin("android")
    alias(libs.plugins.ksp.plugin)
}

android {
    namespace = "com.anthonycr.mezzanine"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.anthonycr.mezzanine"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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

mezzanine {
    files = files(
        "src/main/assets/test.json"
    )
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":mezzanine:core"))
    ksp(project(":mezzanine:processor"))
}
