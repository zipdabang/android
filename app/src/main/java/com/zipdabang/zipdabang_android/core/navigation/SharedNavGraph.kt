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


    navigation(startDestination = SharedScreen.DetailRecipe.route,route = SHARED_ROUTE){
        composable(SharedScreen.DetailRecipe.route){ backStackEntry ->
            RecipeDetailScreen(
                navController = navController,
                recipeId = backStackEntry.arguments?.getInt("recipeId"),
                onClickBackIcon = {},
                onClickProfile = { ownerId -> },
                onClickLike = { recipeId -> },
                onClickScrap = { recipeId -> },
                onClickCart = { keyword -> },
                onClickDelete = { recipeId -> },
                onClickEdit = { recipeId -> }
            )
        }

        composable(SharedScreen.Search.route){
            SearchScreen(navController)
        }

        composable(SharedScreen.SearchRecipeCategory.route,
            arguments = listOf(
                navArgument("categoryId"){ type = NavType.IntType },
                navArgument("keyword") {type = NavType.StringType}
            )){
               val categoryId = it.arguments?.getInt("categoryId")
               val keyword = it.arguments?.getString("keyword")

              SearchCategoryScreen(navController)


        }
        }



    }




