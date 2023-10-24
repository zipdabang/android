package com.zipdabang.zipdabang_android.module.recipes.ui.hot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.common.UiState
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import kotlinx.coroutines.Deferred

@Composable
fun HotRecipeList(
    hotItems: UiState<List<HotRecipeItem>>,
    onRecipeClick: (Int) -> Unit,
    onBlockedRecipeClick: (Int, Int) -> Unit,
    checkLoggedIn: () -> Boolean,
    onScrapClick: (Int) -> Deferred<Boolean>,
    onLikeClick: (Int) -> Deferred<Boolean>,
    likeState: PreferenceToggleState,
    scrapState: PreferenceToggleState,
    setShowLoginRequestDialog: () -> Unit,
    currentPlatform: CurrentPlatform,
    showSnackbar: (String) -> Unit

) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        if (hotItems.isLoading == true) {
            repeat(5) {
                HotRecipeItemLoading()
            }
        } else {
            hotItems.data?.forEachIndexed { index, recipesByOwnerType ->
                HotRecipeItem(
                    index = index + 1,
                    item = recipesByOwnerType,
                    onRecipeClick = onRecipeClick,
                    onBlockedRecipeClick = onBlockedRecipeClick,
                    checkLoggedIn = checkLoggedIn,
                    onLikeClick = onLikeClick,
                    onScrapClick = onScrapClick,
                    likeState = likeState,
                    scrapState = scrapState,
                    setShowLoginRequestDialog = setShowLoginRequestDialog,
                    currentPlatform = currentPlatform,
                    showSnackbar = showSnackbar
                )
            }
        }
    }
}