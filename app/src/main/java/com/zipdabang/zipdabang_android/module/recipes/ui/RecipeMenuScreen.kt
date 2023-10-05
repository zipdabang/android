package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.common.UiState
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.item.recipe.ui.HotRecipe
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerCategory
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeBannerState
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeCategoryState
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RecipeMenuScreen(
    modifier: Modifier,
    onCategoryClick: (Int) -> Unit,
    onOwnerTypeClick: (String) -> Unit,
    onRecipeClick: (Int) -> Unit,
    onBannerClick: (String) -> Unit,
    onLikeClick: (Int) -> Unit,
    onScrapClick: (Int) -> Unit,
    banners: RecipeBannerState,
    hotRecipes: List<TabItem>,
    categories: RecipeCategoryState,
    ownerTypeList: List<OwnerCategory>,
    likeState: StateFlow<PreferenceToggleState>,
    scrapState: StateFlow<PreferenceToggleState>,
    deviceSize: DeviceScreenSize,
    pagerState: PagerState
) {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            item {
                RecipeBanner(
                    bannerState = banners,
                    onClickBanner = onBannerClick
                )

                Spacer(modifier = Modifier.height(16.dp))

                RecipeCategoryList(
                    categoryState = categories,
                    onCategoryClick = onCategoryClick
                )

                Spacer(modifier = Modifier.height(20.dp))

                RecipeOwnerCategoryGroup(
                    onClick = onOwnerTypeClick,
                    ownerTypeList = ownerTypeList
                )

                HotRecipe(
                    tabsList = hotRecipes,
                    pagerState = pagerState,
                    deviceSize = deviceSize,
                    onRecipeClick = onRecipeClick,
                    onLikeClick = onLikeClick,
                    onScrapClick = onScrapClick,
                    likeState = likeState,
                    scrapState = scrapState
                )

                Spacer(modifier = Modifier.height(40.dp))
            }

            /*RecipePreview(
                recipeMapByOwnerType = recipeMapByOwnerType,
                onOwnerTypeClick = onOwnerTypeClick,
                onRecipeClick = onRecipeClick,
                onLikeClick = onLikeClick,
                onScrapClick = onScrapClick,
                likeState = likeState,
                scrapState = scrapState
            )*/


        }
    }
}