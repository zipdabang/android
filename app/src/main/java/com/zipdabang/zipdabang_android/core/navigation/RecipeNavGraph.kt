package com.zipdabang.zipdabang_android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.home.ui.GuideScreen
import com.zipdabang.zipdabang_android.module.home.ui.HomeScreen
import com.zipdabang.zipdabang_android.module.recipes.ui.RecipesScreen


fun NavGraphBuilder.RecipeNavGraph(navController: NavController) {


    navigation(startDestination = RecipeScreen.Home.route, route = RECIPES_ROUTE) {
        composable(RecipeScreen.Home.route) {
            RecipesScreen(
                onGoToDetail = {
                    navController.navigate(SharedScreen.DetailRecipe.route)
                }
            )
        }
    }
}




