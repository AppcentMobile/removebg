# Removebg - Background Removal Android Library

[![Release](https://jitpack.io/v/com.github.erenalpaslan/removebg.svg)](https://jitpack.io/#com.github.erenalpaslan/removebg)  [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)<br>

The "removebg" Android library simplifies background removal from images using the U2Net model. With this library, you can quickly integrate background removal functionality into your Android app.

| Original Image | Background Removed Image |
| -------------- | ------------------------- |
| ![Original Image](previews/1695711470810.jpg) | ![Background Removed Image](previews/1695711470815.jpg) |
| ![Original Image](previews/1695711470819.jpg) | ![Background Removed Image](previews/1695711470823.jpg) |
| ![Original Image](previews/1695711470828.jpg) | ![Background Removed Image](previews/1695711470832.jpg) |


## Installation

To include "removebg" in your Android project, add the following to your Gradle files:

```gradle
allprojects {
    repositories {
      ...
      maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.erenalpaslan:removebg:1.0.3'
}
```

## Usage

Here's a quick example of how to use the "removebg" library in your Android app:

``` kotlin
val remover = RemoveBg(context)

remover.clearBackground(inputImage.value).collect { output ->
    outputImage.value = output
}
```

## Contributions and Issues

Contributions and issue reports are welcome! Feel free to contribute to the development of this library or report any problems you encounter.

## License

 * MIT ([LICENSE](LICENSE) or https://opensource.org/license/mit/)



