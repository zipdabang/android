package com.zipdabang.zipdabang_android.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.recipes.ui.RecipeScreen


fun NavGraphBuilder.RecipeNavGraph(navController: NavController) {


    navigation(startDestination = RecipeScreen.Home.route, route = RECIPES_ROUTE) {
        composable(RecipeScreen.Home.route) {
            RecipeScreen(
                onGoToDetail = {
                    navController.navigate(SharedScreen.DetailRecipe.route)
                }
            )
        }
    }
}




