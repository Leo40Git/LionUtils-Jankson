# LionUtils Jankson Extension
LionUtils extension that provides an ObjectFormat binding for Jankson.

# Usage
This library is designed to be included in the mods that use it via Jar-in-Jar (JiJ) technology.

First, add my Fabric mod repository to your `build.gradle`:
```gradle
repositories {
	maven { url 'https://dl.bintray.com/adudecalledleo/mcmods.fabric' }
}
```
Then, to include it:
```gradle
dependencies {
  modApi "adudecalledleo.mcmods.fabric:lionutils-jankson:${project.lionutils_jankson_version}"
  include "adudecalledleo.mcmods.fabric:lionutils-jankson:${project.lionutils_jankson_version}"
}
```
Make sure you also add [LionUtils](https://github.com/Leo40Git/LionUtils)!

The latest version can be found here:  
[![Download](https://api.bintray.com/packages/adudecalledleo/mcmods.fabric/lionutils-jankson/images/download.svg)](https://bintray.com/adudecalledleo/mcmods.fabric/lionutils-jankson/_latestVersion)
