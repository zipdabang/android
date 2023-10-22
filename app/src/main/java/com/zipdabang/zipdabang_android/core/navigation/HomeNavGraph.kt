package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.guide.ui.GuideScreen1
import com.zipdabang.zipdabang_android.module.home.ui.HomeScreen
import com.zipdabang.zipdabang_android.module.main.NotificationViewModel
import com.zipdabang.zipdabang_android.module.main.common.FCMData
import com.zipdabang.zipdabang_android.module.main.common.NotificationTarget


fun NavGraphBuilder.HomeNavGraph(
    navController: NavController
){
    navigation(startDestination = HomeScreen.Home.route,route = HOME_ROUTE){
        composable(HomeScreen.Home.route){



            HomeScreen(
                navController = navController,
                onGuide1Click = {
                    (navController.navigate(HomeScreen.Guide1.route))
                },
                onRecipeItemClick = {
                        recipeid -> navController.navigate(SharedScreen.DetailRecipe.passRecipeId(recipeid))
                }
            )
        }
        composable(HomeScreen.Guide1.route){
            GuideScreen1()
        }
    }






}