package com.zipdabang.zipdabang_android.module.item.recipe.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.common.UiState
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.ui.component.CategoryPager
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HotRecipe(
    modifier: Modifier = Modifier,
    tabsList: List<TabItem>,
    pagerState: PagerState,
    deviceSize: DeviceScreenSize,
    onRecipeClick: (Int) -> Unit,
    onScrapClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    likeState: StateFlow<PreferenceToggleState>,
    scrapState: StateFlow<PreferenceToggleState>
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HotRecipeTitle()

        CategoryPager(tabsList = tabsList, pagerState = pagerState, deviceSize = deviceSize)
    }
}
