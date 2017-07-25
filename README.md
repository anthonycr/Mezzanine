# Mezzanine
A Java annotation processor that allows you to read static UTF-8 files synchronously.

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

##### Android/Java
```groovy
compile project(':mezzanine')
annotationProcessor project(':mezzanine-compiler')
```

##### Android/Kotlin
```groovy
apply plugin: 'kotlin-kapt'

compile project(':mezzanine')
kapt project(':mezzanine-compiler')
```

##### Java
```groovy
plugins {
    id 'net.ltgt.apt' version '0.10'
}

dependencies {
    compile project(':mezzanine')
    apt project(':mezzanine-compiler')
}
```

### API
- `@FileStream(String path)`: the path to the file relative to the project root.
- Create an `interface` with one method with no parameters and a return type of `String`.
- Annotate that single method with `@FileStream` and pass the path to the file as the value in the annotation.
- Consume the generated implementation of the interface to get the file as a string.
- Files are assumed to be encoded as `UTF-8`.

### Sample

#### Kotlin
```kotlin
interface MyFileReader {

    @FileStream("path/from/root/to/file.json")
    fun readMyFile(): String

}
...

val fileReader = MezzanineGenerator.MyFileReader()

val fileContents = fileReader.readMyFile()

println("File contents: $fileContents")
```

#### Java
```java
public interface MyFileReader {

    @FileStream("path/from/root/to/file.json")
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
