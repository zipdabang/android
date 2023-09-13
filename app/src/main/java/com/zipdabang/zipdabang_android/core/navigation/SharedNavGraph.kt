package com.zipdabang.zipdabang_android.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailScreen
import com.zipdabang.zipdabang_android.module.market.ui.CategoryMarketScreen
import com.zipdabang.zipdabang_android.module.search.ui.SearchCategoryScreen
import com.zipdabang.zipdabang_android.module.search.ui.SearchScreen


fun NavGraphBuilder.SharedNavGraph(navController: NavController){

    navigation(
        startDestination = SharedScreen.DetailRecipe.route,
        route = SHARED_ROUTE
    ){
        composable(
            route = SharedScreen.DetailRecipe.route,
            arguments = listOf(
                navArgument(name = "recipeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            RecipeDetailScreen(
                navController = navController,
                recipeId = backStackEntry.arguments?.getInt("recipeId"),
                onClickBackIcon = {},
                onClickProfile = { ownerId -> },
                onClickCart = { keyword -> },
                onClickDelete = { recipeId -> },
                onClickEdit = { recipeId -> },
                onClickBlock = { userId -> },
                onClickReport = { recipeId -> },
                onClickCommentBlock = { recipeId -> },
                onClickCommentReport = { recipeId -> },
                onClickCommentDelete = { recipeId -> },
                onClickCommentEdit = { recipeId -> },
            )
        }

        composable(SharedScreen.Search.route){
            SearchScreen(
                navController= navController,
                onRecipeItemClick = {
                    recipeid -> navController.navigate(SharedScreen.DetailRecipe.passRecipeId(recipeid))
                }

            )
        }

        composable(SharedScreen.SearchRecipeCategory.route,
            arguments = listOf(
                navArgument("categoryId"){ type = NavType.IntType },
                navArgument("keyword") {type = NavType.StringType}
            )){
               val categoryId = it.arguments?.getInt("categoryId")
               val keyword = it.arguments?.getString("keyword")

              SearchCategoryScreen(navController,
                  onRecipeItemClick = {
                          recipeid -> navController.navigate(SharedScreen.DetailRecipe.passRecipeId(recipeid))
                  }
                  )

        }
        }



    }




