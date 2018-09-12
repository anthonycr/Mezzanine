buildscript {
    ext.kotlinVersion = '1.2.61'
    ext.dokkaVersion = '0.9.15'
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.1.4'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        classpath "org.jetbrains.dokka:dokka-gradle-plugin:$dokkaVersion"
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

subprojects {
    group = 'com.anthonycr.mezzanine'
    version = '1.1.1'
}

ext {
    versionName = '1.1.1'

    publishGroupId = 'com.anthonycr.mezzanine'

    // set $ext.publishArtifactId in sub project
    baseArtifactId = "mezzanine"
}

