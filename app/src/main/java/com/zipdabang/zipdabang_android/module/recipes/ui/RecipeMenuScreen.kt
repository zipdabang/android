package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerCategory
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeBannerState
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeCategoryState
import com.zipdabang.zipdabang_android.ui.component.LoginRequestDialog

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RecipeMenuScreen(
    modifier: Modifier,
    onCategoryClick: (Int) -> Unit,
    onOwnerTypeClick: (String) -> Unit,
    onBannerClick: (String) -> Unit,
    banners: RecipeBannerState,
    hotRecipes: List<TabItem>,
    categories: RecipeCategoryState,
    ownerTypeList: List<OwnerCategory>,
    deviceSize: DeviceScreenSize,
    pagerState: PagerState,
    onLoginRequest: () -> Unit,
    showSnackbar: (String) -> Unit
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

                RecipeCategoryList(
                    categoryState = categories,
                    onCategoryClick = onCategoryClick
                )

                RecipeOwnerCategoryGroup(
                    onClick = onOwnerTypeClick,
                    ownerTypeList = ownerTypeList
                )

                HotRecipe(
                    tabsList = hotRecipes,
                    pagerState = pagerState,
                    deviceSize = deviceSize,
                    onLoginRequest = onLoginRequest,
                    showSnackbar = showSnackbar
                )
            }
        }
    }
}