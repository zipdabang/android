package com.zipdabang.zipdabang_android.core.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.module.main.common.FCMData
import com.zipdabang.zipdabang_android.module.main.MainScreen
import com.zipdabang.zipdabang_android.module.main.NotificationViewModel
import com.zipdabang.zipdabang_android.module.main.common.NotificationTarget
import com.zipdabang.zipdabang_android.module.splash.ui.SplashScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    outerNavController: NavHostController,
    fcmData: FCMData?
) {

    val innerNavController = rememberNavController()
    Log.d("RootNavGraph", fcmData.toString())

    NavHost(
        navController = outerNavController,
        startDestination = SPLASH_ROUTE,
        route = ROOT_ROUTE
    ) {
        composable(SPLASH_ROUTE) {
            SplashScreen(
                onTokenValid = {
                    outerNavController.navigate(MAIN_ROUTE) {
                        popUpTo(SPLASH_ROUTE) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onTokenInvalid = {
                    outerNavController.navigate(AuthScreen.SignIn.route) {
                        popUpTo(SPLASH_ROUTE) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNotificationClick = {

                },
                fcmData = fcmData
            )
        }

        authNavGraph(
            navController = outerNavController
        )

        composable(route = MAIN_ROUTE) {
           MainScreen(
               outerNavController = outerNavController,
               fcmData = fcmData
           )
        }

        /* composable(
            route = SharedScreen.DetailRecipe.route,
            arguments = listOf(
                navArgument(name = "recipeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val recipeCommentViewModel = hiltViewModel<RecipeCommentViewModel>()

            val recipeId = backStackEntry.arguments?.getInt("recipeId")

            RecipeDetailScreen(
                recipeId = recipeId,
                onClickBackIcon = {
                    outerNavController.popBackStack()
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
        }*/
    }
}