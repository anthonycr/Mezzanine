Change Log
==========

Version 2.2.0 *(2025-11-17)*
----------------------------
- Multi-module support through allowing consumers to control which module the `mezzanine` function is generated in.
- Adds `generateMezzanine` property to Gradle extension, defaults to `true` for backwards compatibility.
- For consumers with mezzanine usage isolated to one module, nothing changes.
- Updated Kotlin to 2.2.21
- Updated KSP to 2.3.2

Version 2.1.0 *(2025-05-23)*
----------------------------
- Updated KotlinPoet to 2.2.0
- Updated Kotlin to 2.1.21

Version 2.0.2 *(2025-04-06)*
----------------------------
- Change back Mezzanine Gradle dependency application to manual due to edge cases with KSP plugin application.

Version 2.0.1 *(2025-04-01)*
----------------------------
- Automatically apply Mezzanine Gradle dependencies when the Mezzanine plugin is applied.

Version 2.0.0 *(2025-03-16)*
----------------------------
- New Gradle plugin and Kotlin symbol processing architecture replaces old Java annotation processing design.
- Gradle plugin and Kotlin symbol processor versions are tied together.

Version 1.1.1 *(2018-04-08)*
----------------------------
- Fixed warnings caused by logging on latest versions of the Kotlin compiler by changing logging behavior.

Version 1.1.0 *(2018-01-15)*
----------------------------
- Added `mezzanine.projectPath` annotation processing argument (specified in gradle) which is used to determine the project root.

Version 1.0.2 *(2017-12-06)*
----------------------------
- Mezzanine now logs the resolved path of the file to aid in debugging if the file cannot be found.

Version 1.0.1 *(2017-11-04)*
----------------------------
- Mezzanine generated class is now `final` and has a `private` constructor.
- Improved error messaging in certain cases of misuse.
- Replaced synchronous RxJava design with use of Kotlin's Sequences.
- Added unit tests.
- Updated project to use gradle 4.1.

Version 1.0.0 *(2017-07-30)*
----------------------------
- Initial release
