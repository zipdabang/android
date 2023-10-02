package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerCategory
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeBannerState
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeCategoryState
import kotlinx.coroutines.flow.StateFlow

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
    categories: RecipeCategoryState,
    ownerTypeList: List<OwnerCategory>,
    likeState: StateFlow<PreferenceToggleState>,
    scrapState: StateFlow<PreferenceToggleState>
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

                Spacer(modifier = Modifier.height(20.dp))

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