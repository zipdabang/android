package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSort
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSubtitleState
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeListScreen
import com.zipdabang.zipdabang_android.module.recipes.common.QueryType
import com.zipdabang.zipdabang_android.module.recipes.ui.RecipeScreen
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeListViewModel
import kotlinx.coroutines.async


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
                onSearchIconClick = {
                    navController.navigate(SharedScreen.Search.route) {
                        launchSingleTop = true
                    }
                },
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
                    // TODO keyword(검색 키워드) 활용하여 검색 화면으로 이동
                    navController.navigate(
                        route = SharedScreen.SearchForBanner.passQuery(keyword)
                    ) {
                        launchSingleTop = true
                    }
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

            val viewModel = hiltViewModel<RecipeListViewModel>()
            val scope = rememberCoroutineScope()

            val currentPlatform = viewModel.currentPlatform.value
            val total = viewModel.total.value.toString()
            val sortBy = viewModel.sortBy.collectAsState().value

            val categoryState = RecipeSubtitleState(
                categoryId =  backStackEntry.arguments?.getInt("category"),
                ownerType =  backStackEntry.arguments?.getString("ownerType")
            )

            LaunchedEffect(key1 = true) {
                viewModel.setSortBy("latest")

                if (categoryState.categoryId == -1 && categoryState.ownerType != null) {
                    viewModel.getOwnerItemCount(categoryState.ownerType)
                } else {
                    viewModel.getCategoryItemCount(categoryState.categoryId!!)
                }
            }

            val queryType = remember {
                if (categoryState.categoryId == -1 && categoryState.ownerType != null) {
                    // viewModel.getOwnerItemCount(categoryState.ownerType)
                    viewModel.setOwnerType(categoryState.ownerType)
                    QueryType.OWNER
                } else {
                    // viewModel.getCategoryItemCount(categoryState.categoryId!!)
                    viewModel.setCategoryId(categoryState.categoryId!!)
                    QueryType.CATEGORY
                }
            }

            val onLikeClick = { recipeId: Int ->
                scope.async {
                    viewModel.toggleItemLike(recipeId)
                }
            }

            val onScrapClick = { recipeId: Int ->
                scope.async {
                    viewModel.toggleItemScrap(recipeId)
                }
            }

            val recipeList = if (categoryState.categoryId == -1 && categoryState.ownerType != null) {
                viewModel.ownerItems.collectAsLazyPagingItems()
            } else {
                viewModel.categoryItems.collectAsLazyPagingItems()
            }

            val onSortChange = { changedValue: String ->
                when (changedValue) {
                    RecipeSort.LATEST.text -> {
                        viewModel.setSortBy(RecipeSort.LATEST.type)
                    }
                    RecipeSort.LIKES.text -> {
                        viewModel.setSortBy(RecipeSort.LIKES.type)
                    }
                    else -> {
                        viewModel.setSortBy(RecipeSort.FOLLOW.type)
                    }
                }

            }

            val onShareClick = {
                if (currentPlatform == CurrentPlatform.TEMP
                    || currentPlatform == CurrentPlatform.NONE) {
                    showSnackBar("레시피를 작성하려면 로그인이 필요합니다.")
                } else {
                    navController.navigate(
                        route = MyScreen.RecipeWrite.passTempId(0)
                    ) {
                        launchSingleTop = true
                    }
                }
            }

            RecipeListScreen(
                navController = navController,
                currentPlatform = currentPlatform,
                total = total,
                sortBy = sortBy,
                queryType = queryType,
                categoryState = categoryState,
                recipeList = recipeList,
                onSearchIconClick = {
                    navController.navigate(SharedScreen.Search.route) {
                        launchSingleTop = true
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onShareClick = onShareClick,
                onItemClick = { recipeId ->
                    navController.navigate(
                        route = SharedScreen.DetailRecipe.passRecipeId(recipeId)
                    ) {
                        launchSingleTop = true
                    }
                },
                onLoginRequest = {
                    outerNavController.navigate(AUTH_ROUTE){
                        popUpTo(RecipeScreen.RecipeList.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                onSortChange = onSortChange,
                onLikeClick = onLikeClick,
                onScrapClick = onScrapClick,
                showSnackbar = showSnackBar
            )
        }
    }
}




