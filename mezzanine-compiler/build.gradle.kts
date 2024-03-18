plugins {
    kotlin("jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.autoservice)
    kapt(libs.autoservice)
    implementation(libs.javapoet)
    implementation(libs.commons.text)
    implementation(libs.kotlin)

    implementation(project(":mezzanine"))

    testImplementation(libs.junit)
    testImplementation(libs.assertj.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.compiletesting)
    // Used by both auto-service and compile-testing, we need to resolve the version for them.
    testImplementation(libs.guava)
}
