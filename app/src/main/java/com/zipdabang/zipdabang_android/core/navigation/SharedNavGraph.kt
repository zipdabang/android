package com.zipdabang.zipdabang_android.core.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentViewModel
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailScreen
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailViewModel
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

            val recipeId = backStackEntry.arguments?.getInt("recipeId")

            val recipeCommentViewModel = hiltViewModel<RecipeCommentViewModel>()
            val recipeDetailViewModel = hiltViewModel<RecipeDetailViewModel>()

            var commentReportActivated = remember { mutableStateOf(false) }
            var commentBlockActivated = remember { mutableStateOf(false) }
            var recipeReportActivated = remember { mutableStateOf(false) }
            var recipeBlockActivated = remember { mutableStateOf(false) }

            RecipeDetailScreen(
                recipeId = recipeId,
                onClickBackIcon = {
                    navController.popBackStack()
                },
                onClickProfile = { ownerId -> },
                // onClickCart = { keyword -> },
                onClickDelete = {

                },
                onClickEdit = {

                },
                onClickCommentReport = { reportRecipeId, commentId, reportId ->
                    recipeCommentViewModel.reportComment(
                        recipeId = reportRecipeId, commentId = commentId, reportId = reportId
                    )
                },
                onClickCommentBlock = { ownerId ->
                    recipeCommentViewModel.blockUser(ownerId)
                },
                onClickCommentDelete = { deleteRecipeId, commentId ->
                    recipeCommentViewModel.deleteComment(deleteRecipeId, commentId)
                },
                onClickCommentEdit = { editRecipeId, commentId, newContent ->
                    recipeCommentViewModel.editComment(editRecipeId, commentId, newContent)
                }
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
                navArgument("keyword") { type = NavType.StringType }
            )){
               val categoryId = it.arguments?.getInt("categoryId")
               val keyword = it.arguments?.getString("keyword")

              SearchCategoryScreen(
                  onRecipeItemClick = {
                          recipeid -> navController.navigate(SharedScreen.DetailRecipe.passRecipeId(recipeid))
                  }
                  )

        }
        }



    }




