import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("java-gradle-plugin")
    alias(libs.plugins.gradle.publish)
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        languageVersion.set(KotlinVersion.KOTLIN_2_1)
    }
}

java {
    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(gradleApi())
    implementation(libs.commons.text)
    implementation(libs.kotlin)
    implementation(libs.kotlin.gradle)
    implementation(libs.kotlinpoet)
    implementation(libs.ksp.processor)
}

version = property("VERSION").toString()
group = property("GROUP").toString()

gradlePlugin {
    website.set(property("WEBSITE").toString())
    vcsUrl.set(property("VCS_URL").toString())
    plugins {
        create(property("ID").toString()) {
            id = property("ID").toString()
            implementationClass = property("IMPLEMENTATION_CLASS").toString()
            description = property("DESCRIPTION").toString()
            displayName = property("DISPLAY_NAME").toString()
            tags.set(
                listOf(
                    "files",
                    "kotlin",
                    "kotlin-symbol-processing",
                    "kotlin-compiler",
                    "ksp",
                    "mezzanine",
                    "resources",
                )
            )
        }
    }
}
