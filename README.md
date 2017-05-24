# Mezzanine
A Java annotation processor that reads UTF-8 files at compile time.

### What does this do?
A frequent scenario for Android apps is to need to read in a default configuration file on startup and change functionality based on the contents of the configuration file. A convenient way to store this configuration file is is assets, and then to read this file out of assets on startup. This can mean doing expensive disk I/O on the main thread... which is bad.

Mezzanine tries to solve this problem by storing file contents in a String, which is loaded when the APK and class is loaded, rather than requiring additional disk I/O after startup. It acts as an intermediary between files and your Java code. This library is ideal for small files.

Of course, you may already be copying the contents of a file into a String and then reading it at runtime for the performance gain, but this can be a nuisance to maintain. Using Mezzanine, you don't have to worry about escaping the string contents or losing formatting when pasting into the Java class. You can edit in the file, and then read from the method at runtime.

### API
- `@FileStream(String path)`: the path to the file relative to the project root
- Create an `interface` with one method with no parameters and a return type of `String`
- Annotate that single method with `@FileStream` and pass the path to the file as the value in the annotation.
- Consume the generated implementation of the interface to get the file as a string.
- Files are assumed to be encoded as `UTF-8`.

### Sample
```Java
public interface MyFileReader {

    @FileStream("path/from/root/to/file.json")
    String readMyFile();

}

...

MyFileReader fileReader = new Mezzanine.Generator_MyFileReader();

String fileContents = fileReader.readMyFile();
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
