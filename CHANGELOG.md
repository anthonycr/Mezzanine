Change Log
==========

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