package com.zipdabang.zipdabang_android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

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



