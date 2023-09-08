package com.zipdabang.zipdabang_android.module.item.recipe.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSubtitleState
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeListViewModel

@Composable
fun RecipeList(
    modifier: Modifier,
    onItemClick: (Int) -> Unit,
    category: RecipeSubtitleState,
    // 매개변수 명을 content로 해야 composable을 넣을 수 있음
    content: @Composable() () -> Unit,
) {

    val viewModel = hiltViewModel<RecipeListViewModel>()
    val recipeList =
        if (category.categoryId == -1 && category.ownerType != null) {
            Log.d("RecipeList", "ownerType")
            viewModel.getRecipeListByOwnerType(
                ownerType = category.ownerType
            ).collectAsLazyPagingItems()
        } else {
            viewModel.getRecipeListByCategory(
                categoryId = category.categoryId!!
            ).collectAsLazyPagingItems()
        }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        RecipeListContent(
            items = recipeList,
            onItemClick = onItemClick,
            content = content
        )
    }
}