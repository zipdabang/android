package com.zipdabang.zipdabang_android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zipdabang.zipdabang_android.module.basket.ui.BasketScreen
import com.zipdabang.zipdabang_android.module.home.ui.HomeScreen
import com.zipdabang.zipdabang_android.module.market.ui.MarketScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyScreen
import com.zipdabang.zipdabang_android.module.recipes.ui.RecipesScreen

@Composable
fun MainNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = HOME_ROUTE , route = MAIN_ROUTE)
    {
        HomeNavGraph(navController = navController)

        MarketNavGraph(navController = navController)

        BasketNavGraph(navController = navController)

        RecipeNavGraph(navController = navController)

        MyNavGraph(navController = navController)

        SharedNavGraph(navController)

    }

}



