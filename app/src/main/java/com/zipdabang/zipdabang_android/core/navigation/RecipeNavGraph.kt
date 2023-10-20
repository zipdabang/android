package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSubtitleState
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeListScreen
import com.zipdabang.zipdabang_android.module.recipes.ui.RecipeScreen


fun NavGraphBuilder.RecipeNavGraph(
    navController: NavController,
    outerNavController: NavController,
    showSnackBar: (String) -> Unit
) {
    navigation(startDestination = RecipeScreen.Home.route, route = RECIPES_ROUTE) {
        composable(RecipeScreen.Home.route) {

            var showLoginRequestDialog by remember {
                mutableStateOf(false)
            }

            RecipeScreen(
                onCategoryClick = { categoryId ->
                    Log.d("type - category", "$categoryId")
                    navController.navigate(RecipeScreen.RecipeList.passQuery(category = categoryId)) {
                        launchSingleTop = true
                    }
                },
                onOwnerTypeClick = { ownerType ->
                    Log.d("type - ownertype", ownerType)
                    navController.navigate(RecipeScreen.RecipeList.passQuery(ownerType = ownerType)) {
                        launchSingleTop = true
                    }
                },
                onRecipeClick = { recipeId ->
                    navController.navigate(
                        route = SharedScreen.DetailRecipe.passRecipeId(recipeId)
                    ) {
                        launchSingleTop = true
                    }
                },
                onBlockedRecipeClick = { recipeId, ownerId ->
                    navController.navigate(
                        route = SharedScreen.BlockedRecipe.passRecipeId(recipeId, ownerId)
                    ) {
                        launchSingleTop = true
                    }
                },
                onBannerClick = { keyword ->

                },
                onLoginRequest = {
                    outerNavController.navigate(AUTH_ROUTE){
                        popUpTo(MAIN_ROUTE) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                showSnackbar = showSnackBar,
                navController = navController,
                showLoginRequestDialog = showLoginRequestDialog,
                setShowLoginRequestDialog = { changedValue ->
                    showLoginRequestDialog = changedValue
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
                navController = navController,
                categoryState = categoryState,
                onShareClick = {
                    navController.navigate(
                        route = MyScreen.RecipeWrite.route
                    ) {
                        launchSingleTop = true
                    }
                },
                onItemClick = { recipeId ->
                    navController.navigate(
                        route = SharedScreen.DetailRecipe.passRecipeId(recipeId)
                    ) {
                        launchSingleTop = true
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onLoginRequest = {
                    outerNavController.navigate(AUTH_ROUTE){
                        popUpTo(RecipeScreen.RecipeList.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                showSnackbar = showSnackBar
            )
        }
    }
}




