package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.zipdabang.zipdabang_android.module.guide.ui.GuideScreen1
import com.zipdabang.zipdabang_android.module.home.ui.HomeScreen
import com.zipdabang.zipdabang_android.module.home.ui.HomeViewModel
import com.zipdabang.zipdabang_android.module.main.NotificationViewModel
import com.zipdabang.zipdabang_android.module.main.common.FCMData
import com.zipdabang.zipdabang_android.module.main.common.NotificationTarget
import kotlinx.coroutines.async


fun NavGraphBuilder.HomeNavGraph(
    innernavController: NavController,
    outerNavController: NavController,
    showSnackBar: (String) -> Unit
){
    navigation(startDestination = HomeScreen.Home.route,route = HOME_ROUTE){
        composable(HomeScreen.Home.route){
            val scope = rememberCoroutineScope()
            val homeViewModel : HomeViewModel = hiltViewModel()


            val onLikeClick = { recipeId: Int ->
                scope.async {
                  homeViewModel.toggleItemLike(recipeId)
                }
            }

            val onScrapClick = { recipeId: Int ->
                scope.async {
                    homeViewModel.toggleItemScrap(recipeId)
                }
            }
            HomeScreen(
                navController = innernavController,
                onGuide1Click = {
                    (innernavController.navigate(HomeScreen.Guide1.route))
                },
                onRecipeItemClick = {
                        recipeid -> innernavController.navigate(SharedScreen.DetailRecipe.passRecipeId(recipeid))
                },
                onBlockedRecipeClick = {
                        recipeId, ownerId ->
                    innernavController.navigate(
                        route = SharedScreen.BlockedRecipe.passRecipeId(recipeId, ownerId)
                    ) {
                        launchSingleTop = true
                    }
                },
                onGotoOnBoarding = {
                    outerNavController.navigate(
                        route = AuthScreen.SignIn.route
                    )
                },
                onLikeClick = {
                    onLikeClick(it)
                },
                onScrapClick = {
                    onScrapClick(it)
                },
                showSnackBar = {
                    showSnackBar(it)
                }

            )
        }
        composable(HomeScreen.Guide1.route){
            GuideScreen1(
                GoToRecipe ={
                    innernavController.navigate(RecipeScreen.RecipeList.passQuery(0,null))
                }
            )
        }
    }






}