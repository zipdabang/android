package com.zipdabang.zipdabang_android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.detail.recipe.DetailRecipeScreen
import com.zipdabang.zipdabang_android.module.home.ui.GuideScreen
import com.zipdabang.zipdabang_android.module.home.ui.HomeScreen
import com.zipdabang.zipdabang_android.module.market.ui.MarketScreen


fun NavGraphBuilder.SharedNavGraph(navController: NavController){


    navigation(startDestination = SharedScreen.DetailRecipe.route,route = SHARED_ROUTE){
        composable(SharedScreen.DetailRecipe.route){
            DetailRecipeScreen()
        }

    }






}