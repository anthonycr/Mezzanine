apply plugin: 'kotlin'
apply plugin: 'kotlin-kapt'
apply plugin: 'jacoco'

project.ext {
    publishArtifactId = "${project.baseArtifactId}-compiler"
}

dependencies {
    // annotation processing
    def autoServiceVersion = '1.0-rc3'
    implementation "com.google.auto.service:auto-service:$autoServiceVersion"
    kapt "com.google.auto.service:auto-service:$autoServiceVersion"

    // code generation
    implementation 'com.squareup:javapoet:1.11.1'
    implementation 'org.apache.commons:commons-text:1.1'

    // kotlin
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"

    implementation project(':mezzanine')

    testImplementation 'junit:junit:4.12'
    testImplementation 'org.assertj:assertj-core:3.10.0'
    testImplementation 'org.mockito:mockito-core:2.21.0'
    testImplementation 'com.google.testing.compile:compile-testing:0.15'

    // Used by both auto-service and compile-testing, we need to resolve the version for them.
    implementation 'com.google.guava:guava:23.5-jre'
}

compileJava {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}

jacoco {
    toolVersion = '0.7.9' // See http://www.eclemma.org/jacoco/
}

jacocoTestReport {
    reports {
        xml.enabled true
        html.enabled false
    }
}

check.dependsOn jacocoTestReport

// gradlew clean build generateRelease
apply plugin: 'maven'
apply plugin: 'org.jetbrains.dokka'

def groupId = project.publishGroupId
def artifactId = project.publishArtifactId
def version = project.versionName

def localReleaseDest = "${buildDir}/release/${version}"

task dokkaJavadoc(type: org.jetbrains.dokka.gradle.DokkaTask) {
    outputFormat = 'javadoc'
    outputDirectory = "$buildDir/javadoc"
}

task javadocsJar(type: Jar, dependsOn: dokkaJavadoc) {
    classifier = 'javadoc'
    from dokkaJavadoc.outputDirectory
}

task sourcesJar(type: Jar) {
    classifier = 'sources'
    from sourceSets.main.java.srcDirs
}

uploadArchives {
    repositories.mavenDeployer {
        pom.groupId = groupId
        pom.artifactId = artifactId
        pom.version = version
        pom.packaging = 'jar'

        repository(url: "file://${localReleaseDest}")
    }
}

task zipRelease(type: Zip) {
    from localReleaseDest
    destinationDir buildDir
    archiveName "release-${version}.zip"
}

task generateRelease

generateRelease.doLast {
    println "Release ${version} can be found at ${localReleaseDest}/"
    println "Release ${version} zipped can be found ${buildDir}/release-${version}.zip"
}

generateRelease.dependsOn(uploadArchives)
generateRelease.dependsOn(zipRelease)

artifacts {
    archives sourcesJar
    archives javadocsJar
}

compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
compileTestKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
