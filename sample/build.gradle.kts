plugins {
    alias(libs.plugins.ksp.plugin)
    id("com.anthonycr.plugins.mezzanine")
    alias(libs.plugins.android.app)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.anthonycr.mezzanine"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.anthonycr.mezzanine"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
}

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
}

mezzanine {
    files = files(
        "src/main/assets/test1.json"
    )
    generateMezzanine = true
}

dependencies {
    ksp(project(":mezzanine:processor"))
    implementation(project(":mezzanine:core"))
    implementation(project(":sample-library"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.assertj)
    testImplementation(libs.junit)
}
