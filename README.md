# Mezzanine
An annotation processor that allows you to read static UTF-8 files synchronously.

[![Build Status](https://travis-ci.org/anthonycr/Mezzanine.svg?branch=master)](https://travis-ci.org/anthonycr/Mezzanine)

### What does this do?
A frequent scenario for Android apps is to need to read in a default configuration file on startup and
change functionality based on the contents of the configuration file. A convenient, frequently used way
to store this configuration file is in assets, and then to read out of this file on startup. This can
mean doing expensive disk I/O on the main thread - which can cause increased startup time and dropped frames.

Mezzanine solves this problem by storing file contents in a `String` constant, which is loaded when the
application is started, rather than requiring additional disk I/O after startup. It acts as an intermediary
between files and your running Java code. This library is ideal for small files. There is also a hard limit
in the javac compiler where `String` constants cannot exceed 65535 bytes in size.

Of course, you may already be copying the contents of a file into a `String` and then reading it at runtime
for the performance gain, but this can be a nuisance to maintain. Using Mezzanine, you don't have to worry
about escaping the `String` contents or losing formatting. You can edit within the file, and then read from the method at runtime.

### Usage

```groovy
allprojects {
    repositories {
        maven { url "https://dl.bintray.com/anthonycr/android/" }
    }
}
```

##### Android/Java
```groovy
compile 'com.anthonycr.mezzanine:mezzanine:1.0.0'
annotationProcessor 'com.anthonycr.mezzanine:mezzanine-compiler:1.0.0'
```

##### Android/Kotlin
```groovy
apply plugin: 'kotlin-kapt'

compile 'com.anthonycr.mezzanine:mezzanine:1.0.0'
kapt 'com.anthonycr.mezzanine:mezzanine-compiler:1.0.0'
```

##### Java
```groovy
plugins {
    id 'net.ltgt.apt' version '0.10'
}

dependencies {
    compile 'com.anthonycr.mezzanine:mezzanine:1.0.0'
    apt 'com.anthonycr.mezzanine:mezzanine-compiler:1.0.0'
}
```

### API
- `@FileStream(String path)`: the path to the file relative to the project root.
- Create an `interface` with one method with no parameters and a return type of `String`.
- Annotate the interface with `@FileStream` and pass the path to the file as the value in the annotation.
- Consume the generated implementation of the interface to get the file as a string.
- Files are assumed to be encoded as `UTF-8`.
- Files must be less than 65kB, otherwise javac will complain.

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
