package com.zipdabang.zipdabang_android.module.item.recipe.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeListTitle
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeCategoryState
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeListViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun RecipeList(
    modifier: Modifier,
    onItemClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    onScrapClick: (Int) -> Unit,
    category: RecipeListTitle,
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
        } else if (category.categoryId != null && category.ownerType == null) {
            Log.d("RecipeList", "category")
            viewModel.getRecipeListByCategory(
                categoryId = category.categoryId
            ).collectAsLazyPagingItems()
        } else {
            viewModel.getRecipeListByCategory(
                categoryId = category.categoryId!!
            ).collectAsLazyPagingItems()
        }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        content()
        RecipeListContent(
            items = recipeList,
            onLikeClick = onLikeClick,
            onScrapClick = onScrapClick,
            onItemClick = onItemClick
        )
    }
}