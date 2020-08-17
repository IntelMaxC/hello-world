# jfind tool
a lightweight command line tool to search files inside java archives

## Usage

There are two ways to search a file:

* match by file path (ex. to find a class by the package name)
* match by text content (ex. to find a configuration file by a property name)

### Command line arguments

jfind [OPTIONS ...]

* --dir [root path to start search]
  * default value = "."
* --path.match [path to search]
  * if empty the search by path is disabled
* --path.mid [true, false]
  * true = returns all files contain the specified path
  * false = returns all files the name ends exactly with the specified path
  * defalut value = true

### Installation Requirements

JDK 11 or a custom JDK 11 created by jlink

