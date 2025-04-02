# Mezzanine
A Gradle plugin and Kotlin symbol processor that allows you to read static UTF-8 files synchronously.

## What does this do?
Android apps often need to read in a default configuration file on startup and change functionality based on the contents of the configuration file. A convenient, frequently used way to store this configuration file is in assets, and then to read out of this file on startup. This can mean doing expensive disk I/O on the main thread, which increases startup times. You can get around the additional I/O by pasting the contents of the file into a `String` constant, but this can make code review difficult.

Mezzanine solves this problem by generating a class at compile time which stores the file contents in a `String` constant. This is loaded by the class loader, along with your code, when the application is started, rather than requiring additional disk I/O after startup. It acts as an intermediary between files and your running Kotlin code. Instead of needing to paste the contents of the file into the constant yourself, you can store it in its own file and maintain proper separation. For instance, if you are executing JavaScript using a `WebView` or some library like `duktape`, you can store the files in `.js` files without the additional annoyance of needing to perform disk I/O.

Note: There is a hard limit set by the javac compiler where `String` constants cannot exceed `65535` bytes in size.

## Usage

To your application's `build.gradle.kts`, you must apply the Kotlin plugin, the KSP plugin, and the Mezzanine plugin.

```kotlin
plugins {
    kotlin("jvm") version "LATEST_VERSION"
    id("com.google.devtools.ksp") version "LATEST_VERSION"
    id("com.anthonycr.plugins.mezzanine") version "2.0.1"
}
```

Then setup the `mezzanine` extension to pass the file you want to be processed. You can pass as many files as comma separated paths as you need. The path should be relative to the project root.

```kotlin
mezzanine {
    files = files(
        "src/main/assets/test.json"
    )
}
```

After the above has been added to the `build.gradle.kts`, a Kotlin interface must be created in the project.

The interface must
- Have one function with no parameters and a return type of `String`.
- Be annotated with `@FileStream(path)` where `path` is the same value passed in the extension setup in the `build.gradle.kts`.

```kotlin
@FileStream("src/main/assets/test.json")
interface TestJson {
    fun produce(): String
}
```

Then where you need to consume the file, obtain an instance of the file stream using the `mezzanine` function.

```kotlin
import com.anthonycr.mezzanine.mezzanine

val testJson = mezzanine<TestJson>()
val fileContents = testJson.produce()
```

## Additional Considerations
- Files are assumed to be encoded as `UTF-8`.
- Files must be less than `65kB`, otherwise compilation will fail.

## License
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
