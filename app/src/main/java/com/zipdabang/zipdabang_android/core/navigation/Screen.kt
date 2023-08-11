package com.zipdabang.zipdabang_android.core.navigation

const val ROOT_ROUTE = "root"
const val SPLASH_ROUTE = "splash"
const val AUTH_ROUTE = "auth"
const val MAIN_ROUTE = "main"

sealed class AuthScreen(val route: String) {
    object SignIn: AuthScreen(route = "auth/sign_in")
    object Terms: AuthScreen(route = "auth/sign_up/terms")
    object RegisterUserInfo: AuthScreen(route = "auth/sign_up/user_info")
    object RegisterNickname: AuthScreen(route = "auth/sign_up/nickname")
    object RegisterPreferences: AuthScreen(route = "auth/sign_up/preferences")
}

