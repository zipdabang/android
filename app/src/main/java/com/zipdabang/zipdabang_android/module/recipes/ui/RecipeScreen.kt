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
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerCategory
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeMainViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
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

    val ownerTypeList = listOf(
        OwnerCategory(
            groupName = OwnerType.ALL.type,
            title = OwnerType.ALL.title,
            subTitle = OwnerType.ALL.subTitle,
            borderColor = Color(0xFFB8AFAB),
            backgroundColor = ZipdabangandroidTheme.Colors.BlackSesame,
            textColor = ZipdabangandroidTheme.Colors.Typo,
            drawable = R.drawable.recipe_category_zipdabang
        ),
        OwnerCategory(
            groupName = OwnerType.INFLUENCER.type,
            title = OwnerType.INFLUENCER.title,
            subTitle = OwnerType.INFLUENCER.subTitle,
            borderColor = Color(0xFFC29789),
            backgroundColor = ZipdabangandroidTheme.Colors.Strawberry,
            textColor = Color.White,
            drawable = R.drawable.recipe_category_barista
        ),
        OwnerCategory(
            groupName = OwnerType.USER.type,
            title = OwnerType.USER.title,
            subTitle = OwnerType.USER.subTitle,
            borderColor = Color(0xFF856F65),
            backgroundColor = ZipdabangandroidTheme.Colors.Choco,
            textColor = Color.White,
            drawable = R.drawable.recipe_category_our
        )
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
                    ownerTypeList = ownerTypeList,
                    likeState = likeState,
                    scrapState = scrapState
                )
            }
        },
        drawerState = drawerState,
        navController = navController
    )
}