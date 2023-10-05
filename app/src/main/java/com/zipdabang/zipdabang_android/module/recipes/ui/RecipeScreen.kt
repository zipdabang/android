package com.zipdabang.zipdabang_android.module.recipes.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerCategory
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeBannerState
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeCategoryState
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.HotRecipeViewModel
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeMainViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
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
    val hotRecipeViewModel = hiltViewModel<HotRecipeViewModel>()

    val hotAllRecipes = hotRecipeViewModel.hotAllRecipeState.value
    val hotCoffeeRecipes = hotRecipeViewModel.hotCoffeeRecipeState.value
    val hotCaffeineFreeRecipes = hotRecipeViewModel.hotCaffeineFreeRecipeState.value
    val hotTeaRecipes = hotRecipeViewModel.hotTeaRecipeState.value
    val hotAdeRecipes = hotRecipeViewModel.hotAdeRecipeState.value
    val hotSmoothieRecipes = hotRecipeViewModel.hotSmoothieRecipeState.value
    val hotFruitRecipes = hotRecipeViewModel.hotFruitRecipeState.value
    val hotWellBeingRecipes = hotRecipeViewModel.hotWellBeingRecipeState.value

    val banners = viewModel.banners.value
    val categories = viewModel.categoryList.value

    val likeState = viewModel.toggleLikeResult.collectAsState().value
    val scrapState = viewModel.toggleScrapResult.collectAsState().value

    val deviceSize = viewModel.getDeviceSize()
    val pagerState =  rememberPagerState()

    val onLikeClick = { recipeId: Int ->
        viewModel.toggleLike(recipeId = recipeId)
    }

    val onScrapClick = { recipeId: Int ->
        viewModel.toggleScrap(recipeId = recipeId)
    }

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

    val tabs = listOf(
        TabItem.Coffee(
            hotItems = hotCoffeeRecipes,
            onRecipeClick = onRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            likeState = likeState,
            scrapState = scrapState
        ),
        TabItem.CaffeineFree(
            hotItems = hotCaffeineFreeRecipes,
            onRecipeClick = onRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            likeState = likeState,
            scrapState = scrapState
        ),
        TabItem.Tea(
            hotItems = hotTeaRecipes,
            onRecipeClick = onRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            likeState = likeState,
            scrapState = scrapState
        ),
        TabItem.Ade(
            hotItems = hotAdeRecipes,
            onRecipeClick = onRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            likeState = likeState,
            scrapState = scrapState
        ),
        TabItem.Smoothie(
            hotItems = hotSmoothieRecipes,
            onRecipeClick = onRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            likeState = likeState,
            scrapState = scrapState
        ),
        TabItem.Fruit(
            hotItems = hotFruitRecipes,
            onRecipeClick = onRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            likeState = likeState,
            scrapState = scrapState
        ),
        TabItem.WellBeing(
            hotItems = hotWellBeingRecipes,
            onRecipeClick = onRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            likeState = likeState,
            scrapState = scrapState
        ),
        TabItem.All(
            hotItems = hotAllRecipes,
            onRecipeClick = onRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            likeState = likeState,
            scrapState = scrapState
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
                    onBannerClick = onBannerClick,
                    banners = banners,
                    categories = categories,
                    ownerTypeList = ownerTypeList,
                    hotRecipes = tabs,
                    deviceSize = deviceSize,
                    pagerState = pagerState
                )
            }
        },
        drawerState = drawerState,
        navController = navController
    )
}
