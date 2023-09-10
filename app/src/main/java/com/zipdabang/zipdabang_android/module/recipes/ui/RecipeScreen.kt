package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeMainViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import kotlinx.coroutines.launch

@Composable
fun RecipeScreen(
    navController: NavController,
    onCategoryClick: (Int) -> Unit,
    onOwnerTypeClick: (String) -> Unit,
    onRecipeClick: (Int) -> Unit,
    onBannerClick: (String) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val viewModel = hiltViewModel<RecipeMainViewModel>()

    val banners = viewModel.banners.value
    val categories = viewModel.categoryList.value
    val everyoneRecipe = viewModel.everyRecipeState.value
    val influencerRecipe = viewModel.influencerRecipeState.value
    val userRecipe = viewModel.userRecipeState.value

    val likeState = viewModel.toggleLikeResult
    val scrapState = viewModel.toggleScrapResult

    val recipeMapByOwnerType = mapOf(
        OwnerType.ALL to everyoneRecipe,
        OwnerType.INFLUENCER to influencerRecipe,
        OwnerType.USER to userRecipe
    )

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarHome(
                        endIcon1 = R.drawable.ic_topbar_search,
                        endIcon2 = R.drawable.ic_topbar_menu,
                        onClickEndIcon1 = {},
                        onClickEndIcon2 = { scope.launch { drawerState.open() } },
                        centerText = "집다방"
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black,
            ) { padding ->
                RecipeMenuScreen(
                    modifier = Modifier.padding(padding),
                    onCategoryClick = onCategoryClick,
                    onOwnerTypeClick = onOwnerTypeClick,
                    onRecipeClick = onRecipeClick,
                    onBannerClick = onBannerClick,
                    onLikeClick = { recipeId ->
                        viewModel.toggleLike(recipeId = recipeId)
                    },
                    onScrapClick = { recipeId ->
                        viewModel.toggleScrap(recipeId = recipeId)
                    },
                    banners = banners,
                    categories = categories,
                    recipeMapByOwnerType = recipeMapByOwnerType,
                    likeState = likeState,
                    scrapState = scrapState
                )
            }
        },
        drawerState = drawerState,
        navController = navController
    )
}