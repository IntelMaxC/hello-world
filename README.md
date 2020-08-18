# jfind
a lightweight command line tool to search files inside java archives

## Abstract

There are two ways to search a file:

* match by file path (ex. to find a class by the package name)
* match by text content (ex. to find a configuration file by a property name)

## Download

| System | Descritption | Download | Size |
| ---| --- | --- | --- |
| Linux x64  | Full portable version for Linux 64 bit | [jfind-0.1-linux-x64-portable.tar.gz](https://github.com/IntelMaxC/jfind/releases/download/jfind-0.1/jfind-0.1-linux-x64-portable.tar.gz) |  |
| Windows x64  | Full portable version for Windows 64 bit | [jfind-0.1-win-x64-portable.zip](https://github.com/IntelMaxC/jfind/releases/download/jfind-0.1/jfind-0.1-win-x64-portable.zip) | 23 MB |
| JDK 14+ | Only runtimes, required JDK 14+ already installed | [jfind-0.1.jar](https://github.com/IntelMaxC/jfind/releases/download/jfind-0.1/jfind-0.1.jar) | 0.12 MB |


## Installation

### Linux Full Portable Binaries

Follow the instructions to install jfind under /opt folder (you can use another folder)

* untar the archive under /opt folder
  * cd /opt
  * `tar xvzf jfind-0.1-linux-x64-portable.tar.gz`
  * the root jfind directory is /opt/jfind
* set JFIND_HOME as environment variable to point on root diretcory
  * add `export JFIND_HOME="/opt/jfind"` on .bashrc file
* add JFIND_HOME/ to system environment paths
  * add `export PATH=$PATH:$JFIND_HOME/` on .bashrc file
* open a terminal and write `jfind` to display the help

### Windows Full Portable Binaries

Follow the instructions to install jfind under C:\jfind folder (you can use another folder)

* unzip the archive under C: folder
  * the root jfind directory is C:\jfind
* set JFIND_HOME as environment variable to point on C:\jfind diretcory
* add JFIND_HOME/ to system environment paths
* open a terminal and write `jfind` to display the help

### Only Runtime version

* Requires JDK 14+ already installed
* To execute jfind type `java -jar jfind-0.1.jar [OPTIONS...]`

## Command line arguments

`jfind [OPTIONS ...]`

* `--dir [root path to start search]`
  * default value = "."
* `--scan.recursive [true, false]`
  * true = scan recursively inside sub folders and archives
  * false = flat scan
  * default value = true
* `--scan.archives [comma separated list of archives extension to look inside]`
  * is possible to set all valid zip archives
  * default value = "jar,war,ear,esb"
* `--path.match [path to search]`
  * if empty the search by path is disabled
* `--path.mid [true, false]`
  * true = returns all files contain the specified path
  * false = returns all files the name ends exactly with the specified path
  * defalut value = true
* `--text.match [text content to search]`
  * if empty the search by content is disabled
* `--text.files [comma separated list of file extensions to search text]`
  * is possible to use "!extension" to exclude files from scan
  * default value = "properties,xml"
* `--text.ignorecase [true, false]`
  * true = match text without consider the case of characters (slow)
  * false = match text with a byte per byte comparison (fast)
  * default value = false
* `--text.printfound [true, false]`
  * true = print line where text is found
  * false = print only the files where text is found
  * default value = true
* `--help or -h` to display help

### examples

`jfind --path.match "org.apache"`

returns all file occurrences located under .../org/apache/...

`jfind --text.match "myProperty"`

returns all file occurrences that contain myProperty text



