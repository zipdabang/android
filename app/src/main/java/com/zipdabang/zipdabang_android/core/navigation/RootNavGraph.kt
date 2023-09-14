package com.zipdabang.zipdabang_android.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zipdabang.zipdabang_android.module.main.MainScreen
import com.zipdabang.zipdabang_android.module.splash.ui.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
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
            SplashScreen(
                navController = navController,
                onTokenValid = {
                    navController.navigate(MAIN_ROUTE) {
                        popUpTo(SPLASH_ROUTE) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onTokenInvalid = {
                    navController.navigate(AuthScreen.SignIn.route) {
                        popUpTo(SPLASH_ROUTE) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }

            )
        }

        authNavGraph(navController = navController)

        composable(route = MAIN_ROUTE) {
           MainScreen(
               outerNavController = navController
           )
        }




    }
}