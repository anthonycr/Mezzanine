import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("com.anthonycr.mezzanine.plugin")
    alias(libs.plugins.android.app)
    alias(libs.plugins.kotlin.android)
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
        }
    }
}

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
    }
}

java {
    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
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
