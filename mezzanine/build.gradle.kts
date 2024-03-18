apply plugin: 'kotlin'
apply plugin: 'jacoco'

project.ext {
    publishArtifactId = project.baseArtifactId
}

compileJava {
    sourceCompatibility = "1.7"
    targetCompatibility = "1.7"
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
        jvmTarget = "1.6"
    }
}
compileTestKotlin {
    kotlinOptions {
        jvmTarget = "1.6"
    }
}
