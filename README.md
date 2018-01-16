# Mezzanine
An annotation processor that allows you to read static UTF-8 files synchronously.

[![Build Status](https://travis-ci.org/anthonycr/Mezzanine.svg?branch=master)](https://travis-ci.org/anthonycr/Mezzanine)
[![Download](https://api.bintray.com/packages/anthonycr/android/com.anthonycr.mezzanine%3Amezzanine/images/download.svg)](https://bintray.com/anthonycr/android/com.anthonycr.mezzanine%3Amezzanine)
[![codecov](https://codecov.io/gh/anthonycr/Mezzanine/branch/dev/graph/badge.svg)](https://codecov.io/gh/anthonycr/Mezzanine)

### What does this do?
Android apps often need to read in a default configuration file on startup and change functionality based on the contents of the configuration file. A convenient, frequently used way to store this configuration file is in assets, and then to read out of this file on startup. This can mean doing expensive disk I/O on the main thread, which increases startup times. You can get around the additional I/O by pasting the contents of the file into a `String` constant, but this can make code review difficult.

Mezzanine solves this problem by generating a class at compile time which stores the file contents in a `String` constant. This is loaded by the class loader, along with your code, when the application is started, rather than requiring additional disk I/O after startup. It acts as an intermediary between files and your running Java code. Instead of needing to paste the contents of the file into the constant yourself, you can store it in its own file and maintain proper separation. For instance, if you are executing JavaScript using a `WebView` or some library like `duktape`, you can store the files in `.js` files without the additional annoyance of needing to perform disk I/O.

Note: There is a hard limit set by the javac compiler where `String` constants cannot exceed `65535` bytes in size.

### Usage

```groovy
allprojects {
    repositories {
        bintray()
    }
}
```

##### Android/Java
```groovy
dependencies {
  def mezzanineVersion = '1.1.0'
  compile "com.anthonycr.mezzanine:mezzanine:$mezzanineVersion"
  annotationProcessor "com.anthonycr.mezzanine:mezzanine-compiler:$mezzanineVersion"
}

android {
  defaultConfig {
    javaCompileOptions {
      annotationProcessorOptions {
        arguments = [
          "mezzanine.projectPath", project.rootDir
        ]
      }
    }
  }
}
```

##### Kotlin
```groovy
apply plugin: 'kotlin-kapt'

dependencies {
  def mezzanineVersion = '1.1.0'
  compile "com.anthonycr.mezzanine:mezzanine:$mezzanineVersion"
  kapt "com.anthonycr.mezzanine:mezzanine-compiler:$mezzanineVersion"
}

kapt {
    arguments {
        arg("mezzanine.projectPath", project.rootDir)
    }
}
```

##### Java
```groovy
plugins {
    id 'net.ltgt.apt' version '0.10'
}

dependencies {
    def mezzanineVersion = '1.1.0'
    compile "com.anthonycr.mezzanine:mezzanine:$mezzanineVersion"
    apt "com.anthonycr.mezzanine:mezzanine-compiler:$mezzanineVersion"
}

gradle.projectsEvaluated {
  tasks.withType(JavaCompile) {
    aptOptions.processorArgs = [
      "mezzanine.projectPath", project.rootDir
    ]
  }
}
```

### API
- `@FileStream(String path)`: the path to the file relative to the project root.
- `mezzanine.projectPath` annotation processing argument is used to determine the absolute path of the project root. If not provided, mezzanine will use the root accessible to the javac instance.
- Create an `interface` with one method with no parameters and a return type of `String`.
- Annotate the interface with `@FileStream` and pass the path to the file as the value in the annotation.
- Consume the generated implementation of the interface to get the file as a string.
- Files are assumed to be encoded as `UTF-8`.
- Files must be less than `65kB`, otherwise compilation will fail.

### Sample

#### Kotlin
```kotlin
@FileStream("path/from/root/to/file.json")
interface MyFileReader {

    fun readMyFile(): String

}
...

val fileReader = MezzanineGenerator.MyFileReader()

val fileContents = fileReader.readMyFile()

println("File contents: $fileContents")
```

#### Java
```java
@FileStream("path/from/root/to/file.json")
public interface MyFileReader {

    String readMyFile();

}
...

MyFileReader fileReader = new MezzanineGenerator.MyFileReader();

String fileContents = fileReader.readMyFile();

System.out.println("File contents: " + fileContents);
```

### License
````
Copyright 2017 Anthony Restaino

Licensed under the Apache License, Version 2.0 (the "License"); you may 
not use this file except in compliance with the License. You may obtain 
a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
License for the specific language governing permissions and limitations 
under the License.
````
