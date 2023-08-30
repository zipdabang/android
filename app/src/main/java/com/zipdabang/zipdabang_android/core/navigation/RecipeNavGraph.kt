package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSubtitleState
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeListScreen
import com.zipdabang.zipdabang_android.module.recipes.ui.RecipeScreen


fun NavGraphBuilder.RecipeNavGraph(navController: NavController) {


    navigation(startDestination = RecipeScreen.Home.route, route = RECIPES_ROUTE) {
        composable(RecipeScreen.Home.route) {
            RecipeScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate(RecipeScreen.RecipeList.passQuery(category = categoryId))
                },
                onOwnerTypeClick = { ownerType ->
                    Log.d("ownertype", ownerType)
                    navController.navigate(RecipeScreen.RecipeList.passQuery(ownerType = ownerType))
                },
                onRecipeClick = { recipeId ->

                },
                onLikeClick = { recipeId ->

                },
                onScrapClick = { recipeId ->

                },
                onBannerClick = { keyword ->

                }
            )
        }

        composable(
            route = RecipeScreen.RecipeList.route,
            arguments = listOf(
                navArgument("category") {
                    defaultValue = -1
                    type = NavType.IntType
                },
                navArgument("ownerType") {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val categoryState = RecipeSubtitleState(
                categoryId =  backStackEntry.arguments?.getInt("category"),
                ownerType =  backStackEntry.arguments?.getString("ownerType")
            )
            RecipeListScreen(
                categoryState = categoryState,
                onShareClick = {

                },
                onItemClick = {

                },
                onLikeClick = {

                },
                onScrapClick = {

                }
            )
        }
    }
}




