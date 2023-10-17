package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.item.recipe.ui.HotRecipeTitle
import com.zipdabang.zipdabang_android.ui.component.CategoryPager

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HotRecipe(
    modifier: Modifier = Modifier,
    tabsList: List<TabItem>,
    pagerState: PagerState,
    deviceSize: DeviceScreenSize,
    onLoginRequest: () -> Unit,
    showSnackbar: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp)
    ) {
        HotRecipeTitle()
        CategoryPager(
            tabsList = tabsList,
            pagerState = pagerState,
            deviceSize = deviceSize,
            onLoginRequest = onLoginRequest,
            showSnackbar = showSnackbar
        )
    }
}
