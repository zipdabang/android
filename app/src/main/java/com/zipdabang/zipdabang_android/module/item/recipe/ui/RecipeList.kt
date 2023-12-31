package com.zipdabang.zipdabang_android.module.item.recipe.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSort
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSubtitleState
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import kotlinx.coroutines.Deferred

@Composable
fun RecipeList(
    modifier: Modifier,
    onItemClick: (Int) -> Unit,
    sortList: List<RecipeSort>,
    onSortChange: (String) -> Unit,
    total: String,
    checkLoggedIn: () -> Boolean,
    // 매개변수 명을 content로 해야 composable을 넣을 수 있음
    recipeList: LazyPagingItems<RecipeItem>,
    onToggleLike: (Int) -> Deferred<Boolean>,
    onToggleScrap: (Int) -> Deferred<Boolean>,
    lazyGridState: LazyGridState,
    showSnackbar: (String) -> Unit,
    content: @Composable() () -> Unit,
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        RecipeListContent(
            items = recipeList,
            onItemClick = onItemClick,
            total = total,
            sortList = sortList,
            onSortChange = onSortChange,
            checkLoggedIn = checkLoggedIn,
            onToggleLike = onToggleLike,
            onToggleScrap = onToggleScrap,
            lazyGridState = lazyGridState,
            showSnackbar = showSnackbar,
            content = content
        )
    }
}