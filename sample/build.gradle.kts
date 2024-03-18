apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'

android {
    compileSdkVersion 27
    buildToolsVersion '27.0.3'
    defaultConfig {
        applicationId "com.anthonycr.mezzanine.sample"
        minSdkVersion 16
        targetSdkVersion 27
        versionCode 1
        versionName "1.0"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

kapt {
    arguments {
        arg("mezzanine.projectPath", project.rootDir)
    }
}

dependencies {
    implementation 'com.android.support:appcompat-v7:27.0.2'

    implementation project(':mezzanine')
    kapt project(':mezzanine-compiler')
}
