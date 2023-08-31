package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.my.ui.MyScreen


fun NavGraphBuilder.MyNavGraph(navController: NavController) {

    navigation(startDestination = MyScreen.Home.route, route = MY_ROUTE) {
        composable(MyScreen.Home.route) {
            MyScreen(
                onClickLogout = {
                    navController.navigate(AuthScreen.SignIn.route)
                    Log.e("signup-tokens","로그아웃 클릭, onClick 실행 중")
                }
            )
        }
    }
}




