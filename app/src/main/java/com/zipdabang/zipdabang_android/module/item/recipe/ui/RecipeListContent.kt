package com.zipdabang.zipdabang_android.module.item.recipe.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeListViewModel

@Composable
fun RecipeListContent(
    items: LazyPagingItems<RecipeItem>,
    onItemClick: (Int) -> Unit,
    content: @Composable() () -> Unit,
) {

    val viewModel = hiltViewModel<RecipeListViewModel>()

    LaunchedEffect(key1 = items.loadState) {
        if (items.loadState.refresh is LoadState.Loading) {
/*            items(count = 10) {
                RecipeCardLoading()
            }*/
            Log.d("RecipeList - content", "loading...")
        } else if (items.loadState.refresh is LoadState.Error) {
            Log.d("RecipeList - content", (items.loadState.refresh as LoadState.Error).error.message ?: "unexpected error")
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Log.d("Error", items.loadState.toString())

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            content()
        }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                all = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                count = items.itemCount,
                key = items.itemKey { recipe ->
                    recipe.recipeId
                },
                contentType = items.itemContentType { "images" }
            ) { index: Int ->
                val recipeItem = items[index]

                if (recipeItem != null) {
                    RecipeCard(
                        recipeId = recipeItem.recipeId,
                        title = recipeItem.recipeName,
                        user = recipeItem.nickname,
                        thumbnail = recipeItem.thumbnailUrl,
                        date = recipeItem.createdAt,
                        likes = recipeItem.likes,
                        comments = recipeItem.comments ?: 0,
                        isLikeSelected = recipeItem.isLiked,
                        isScrapSelected = recipeItem.isScrapped,
                        onLikeClick = { recipeId ->
                            viewModel.toggleLike(recipeId)
                            viewModel.toggleLikeResult.value.isSuccessful == true
                        },
                        onScrapClick = { recipeId ->
                            viewModel.toggleScrap(recipeId)
                            viewModel.toggleScrapResult.value.isSuccessful == true
                        },
                        onItemClick = onItemClick
                    )
                }
            }

            if (items.loadState.refresh is LoadState.Loading) {
                items(count = 10) {
                    RecipeCardLoading()
                }
            }
        }
    }
}