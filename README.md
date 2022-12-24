Android Assignment
This is a simple Kotlin MVVM-clean-architecture demonstration. To build this project I have used below standard libraries of Android

Retrofit 2 by Square for Networking
Moshi Json Converter
Hilt Dependency Injection
Coroutines
Mockk for Unit testing.
Glide Image Loading
Architecture flow
Activity/Fragment -> ViewModel -> UseCase -> Repository -> NetworkService

Code Highlights
By using last.fm API to fetch the top user list and render the UserListFragment, which is attached on MainActivity.

API detail : https://api.github.com/repos/square/retrofit/stargazers<br />
&emsp;&emsp;&emsp;&emsp;&emsp;https://api.github.com//users/{user}

When user tap on any list item, currently opening a detail User detail.

Build Info
Android Studio - Dolphin, Compile SDK - 32, MinSDK - 21, Target - 32
