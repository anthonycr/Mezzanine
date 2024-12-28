import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.maven.publish)
}

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
    compilerOptions {
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
    }
}

dependencies {
    implementation(project(":mezzanine:core"))

    implementation(libs.kotlin)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
    implementation(libs.ksp.api)

    testImplementation(libs.junit)
}

mavenPublishing {
//    signAllPublications()
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    coordinates(artifactId = "processor")
}