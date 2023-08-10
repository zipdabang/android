package com.zipdabang.zipdabang_android.module.bottom.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zipdabang.zipdabang_android.module.basket.ui.BasketScreen
import com.zipdabang.zipdabang_android.module.home.HomeGraph
import com.zipdabang.zipdabang_android.module.market.ui.MarketScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyScreen
import com.zipdabang.zipdabang_android.module.recipes.ui.RecipesScreen

@Composable
fun Navigation(
    navController: NavHostController,
){
    NavHost(navController =  navController, startDestination = BottomGraph.Home){
        composable(BottomGraph.Market){
            MarketScreen()
        }
        composable(BottomGraph.Basket){
            BasketScreen()
        }
        HomeGraph(navController)

        composable(BottomGraph.Recipes){
            RecipesScreen()
        }
        composable(BottomGraph.My){
            MyScreen()
        }

    }
}


object BottomGraph{
    const val Home = "home_graph"
    const val Market = "market"
    const val Basket = "basket"
    const val Recipes = "recipes"
    const val My = "my"
}





