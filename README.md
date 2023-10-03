# Zipdabang

![Static Badge](https://img.shields.io/badge/version-2.0.0-blue)(Development In Progress)

### Application for Home Cafe Information & Market

<br>

![image](https://github.com/zipdabang/android/assets/101035437/a6de08ad-4599-45e9-a8a3-bc3643d02ba0)

### Contact Us

* Email : zipdabang.app@gmail.com

<br>

## Zipdabang-Android Developers

<table>
    <tr><th>이름</th><th>담당 파트</th></tr>
    <tr><td>강하현</td><td>홈, 마켓, 더보기</td></tr>
    <tr><td>김기문</td><td>OAuth 로그인, 레시피, 마켓</td></tr>
    <tr><td>김예은</td><td>회원가입, 내집다방</td></tr>
</table>

<br>

## Tools

![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-039BE5?style=for-the-badge&logo=Firebase&logoColor=white)
![Jira](https://img.shields.io/badge/jira-%230A0FFF.svg?style=for-the-badge&logo=jira&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white)

<br>

## App Version
* minSdkVersion : 24
* targetSdkVersion : 33

<br>

## Tech Stack

![Static Badge](https://img.shields.io/badge/100%25-Kotlin-blue)
![Static Badge](https://img.shields.io/badge/100%25-Compose-green)

* [100% Kotlin](https://kotlinlang.org/)
    + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
    + [Flow](https://kotlinlang.org/docs/flow.html)
    + [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html)
* [Retrofit2 + OkHttp3](https://square.github.io/retrofit/)
* [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
* [Jetpack](https://developer.android.com/jetpack)
    * [100% Compose](https://developer.android.com/jetpack/compose) 
    * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)
    * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
    * [AAC ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [Room](https://developer.android.com/jetpack/androidx/releases/room)
* [Dagger-Hilt](https://dagger.dev/hilt/) - DI
* [Coil](https://github.com/coil-kt/coil) - image loading
* [Firebase](https://firebase.google.com/docs) - Google Sign In & FCM
* [Kakao SDK](https://developers.kakao.com/docs/latest/ko/kakaologin/android) - Kakao Sign In
* Modern Architecture
    * [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
    * Single activity architecture
      using [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)
    * MVVM
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture)
      ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
      , [Kotlin Flow](https://kotlinlang.org/docs/flow.html)
      , [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
    * [Android KTX](https://developer.android.com/kotlin/ktx)
* UI
    * [Jetpack Compose](https://developer.android.com/jetpack/compose)
    * [Material Design 3](https://m3.material.io/)

<br>

## Architecture

### Package Structure

![image](https://github.com/kmkim2689/paging3-unsplash/assets/101035437/beb89b13-8d1c-4419-874f-199772d00c53)

* common
  * Classes/Files used in no less than two modules
    * ExceptionsResource/ResponseBody Wrapper Class
    * Response Code Enum Class
    * Constants
    
* core
  * Core utilities(Libraries...) used across the application
    * Database for Paging
    * Network
    * FCM
    * Navigation
    * Global AppModule for DI

* entity
  * Tables(data classes) used in the database

* module
  * Divided by feature
    * dividing the entire app into smaller features
  * Package Structure for each module
    * data
    * di
    * domain
    * util
    * ui
    * use_case

* ui
  * Components commonly used across the application(no less than two modules)
  * Composable Functions

<br>

### Module Architecture

![image](https://github.com/kmkim2689/paging3-unsplash/assets/101035437/f83d14ee-314a-42e8-a5e2-d78c72c140dd)

<br>

## License
```
MIT License

Copyright (c) 2023 Zipdabang

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
