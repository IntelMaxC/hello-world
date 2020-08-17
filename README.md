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
* --scan.recursive [true, false]
  * true = scan recursively inside sub folders and archives
  * false = flat scan
  * default value = true
* --scan.archives [comma separated list of archives extension to look inside]
  * is possible to set all valid zip archives
  * default value = "jar,war,ear,esb"
* --path.match [path to search]
  * if empty the search by path is disabled
* --path.mid [true, false]
  * true = returns all files contain the specified path
  * false = returns all files the name ends exactly with the specified path
  * defalut value = true

### Installation Requirements

JDK 11 or a custom JDK 11 created by jlink

