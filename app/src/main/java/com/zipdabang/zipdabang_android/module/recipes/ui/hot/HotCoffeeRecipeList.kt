package com.zipdabang.zipdabang_android.module.recipes.ui.hot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.common.UiState
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HotRecipeList(
    hotItems: UiState<List<HotRecipeItem>>,
    onRecipeClick: (Int) -> Unit,
    onScrapClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    likeState: PreferenceToggleState,
    scrapState: PreferenceToggleState
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        hotItems.data?.forEachIndexed { index, recipesByOwnerType ->
            HotRecipeItem(
                index = index + 1,
                item = recipesByOwnerType,
                onRecipeClick = onRecipeClick,
                onLikeClick = onLikeClick,
                onScrapClick = onScrapClick,
                likeState = likeState,
                scrapState = scrapState
            )
        }
    }
}