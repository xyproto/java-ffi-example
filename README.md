# java-ffi-example

Example use of FFI for Java >= 22.

Print the contents of `results.txt` using `fopen`, `fgets` and `fclose` from the system libc.

## Building and running

### Prerequisites

Set the `JAVA_HOME` environment variable to the Java installation path (needs to be Java version 22 or later).

For example, for macOS + homebrew + zsh: `export JAVA_HOME=/opt/homebrew/Cellar/openjdk/22.0.1/`.

`make` is also required.

### Building

    make

### Running

    make run

The contents of `results.txt` will then be printed, using `fopen`, `fgets` and `fclose` from the system libc.

## References

Inspired by:

* https://ifesunmola.com/how-to-use-the-foreign-function-api-in-java-22-to-call-c-libraries/

Which in turn was inspired by:

* https://docs.oracle.com/en/java/javase/21/core/foreign-function-and-memory-api.html
* https://github.com/openjdk/jextract
* https://jdk.java.net/jextract/
* (YouTube) Foreign Function & Memory API - A (Quick) Peek Under the Hood
* (YouTube) Java 22 Launch Stream with Jorn Vernee and Per Ake Minborg
* https://news.ycombinator.com/item?id=34580907#34586552

## General info

* Version: 1.0.0
* License: CC0
