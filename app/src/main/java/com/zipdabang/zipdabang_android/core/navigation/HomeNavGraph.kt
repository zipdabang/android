package com.zipdabang.zipdabang_android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.home.ui.GuideScreen
import com.zipdabang.zipdabang_android.module.home.ui.HomeScreen


fun NavGraphBuilder.HomeNavGraph(navController: NavController){


    navigation(startDestination = HomeScreen.Home.route,route = HOME_ROUTE){
        composable(HomeScreen.Home.route){
            HomeScreen(
                onGoToGuide = {
                    navController.navigate(HomeScreen.Guide.route)
                },
                onGoToDetail = {
                    navController.navigate(SharedScreen.DetailRecipe.route)

                }
            )
        }
        composable(HomeScreen.Guide.route){
            GuideScreen()
        }
    }






}