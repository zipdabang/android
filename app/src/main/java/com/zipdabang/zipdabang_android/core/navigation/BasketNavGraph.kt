package com.zipdabang.zipdabang_android.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.basket.ui.BasketScreen


fun NavGraphBuilder.BasketNavGraph(navController: NavController){


    navigation(startDestination = BasketScreen.Home.route,route = BASKET_ROUTE){
        composable( BasketScreen.Home.route){
            BasketScreen(
                navController = navController
            )
        }

    }






}