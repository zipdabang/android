package com.zipdabang.zipdabang_android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zipdabang.zipdabang_android.module.main.MainScreen
import com.zipdabang.zipdabang_android.module.splash.SplashScreen

@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SPLASH_ROUTE,
        route = ROOT_ROUTE
    ) {
        composable(SPLASH_ROUTE) {
            SplashScreen(navController)
        }

        authNavGraph(navController = navController)

        composable(route = MAIN_ROUTE) {
           MainScreen()
        }


    }
}