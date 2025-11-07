plugins {
    alias(libs.plugins.ksp.plugin)
    id("com.anthonycr.plugins.mezzanine")
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
}

mezzanine {
    files = files(
        "src/main/assets/test.txt"
    )
}

dependencies {
    ksp(project(":mezzanine:processor"))
    implementation(project(":mezzanine:core"))
}
