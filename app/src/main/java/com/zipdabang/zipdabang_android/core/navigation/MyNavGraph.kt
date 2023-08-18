package com.zipdabang.zipdabang_android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.home.ui.GuideScreen
import com.zipdabang.zipdabang_android.module.home.ui.HomeScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyScreen
import com.zipdabang.zipdabang_android.module.recipes.ui.RecipesScreen


fun NavGraphBuilder.MyNavGraph(navController: NavController) {


    navigation(startDestination = MyScreen.Home.route, route = MY_ROUTE) {
        composable(MyScreen.Home.route) {
            MyScreen()
        }
    }
}




