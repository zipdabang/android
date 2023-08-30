package com.zipdabang.zipdabang_android.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.detail.recipe.RecipeDetailScreen


fun NavGraphBuilder.SharedNavGraph(navController: NavController){


    navigation(startDestination = SharedScreen.DetailRecipe.route,route = SHARED_ROUTE){
        composable(SharedScreen.DetailRecipe.route){ backStackEntry ->
            RecipeDetailScreen(
                navController = navController,
                recipeId = backStackEntry.arguments?.getInt("recipeId"),
                onClickBackIcon = {},
                onClickProfile = { ownerId -> },
                onClickLike = { recipeId -> },
                onClickScrap = { recipeId -> },
                onClickCart = { keyword -> }
            )
        }

    }






}