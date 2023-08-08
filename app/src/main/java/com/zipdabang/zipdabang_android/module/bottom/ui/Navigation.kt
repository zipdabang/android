package com.zipdabang.zipdabang_android.module.bottom.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zipdabang.zipdabang_android.module.basket.ui.BasketScreen
import com.zipdabang.zipdabang_android.module.home.ui.HomeScreen
import com.zipdabang.zipdabang_android.module.market.ui.MarketScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyScreen
import com.zipdabang.zipdabang_android.module.recipes.ui.RecipesScreen

@Composable
fun Navigation(
    navController: NavHostController
){
    NavHost(navController =  navController, startDestination = "home"){
        composable("market"){
            MarketScreen()
        }
        composable("basket"){
            BasketScreen()
        }
        composable("home"){
            HomeScreen()
        }
        composable("recipes"){
            RecipesScreen()
        }
        composable("my"){
            MyScreen()
        }

    }
}






