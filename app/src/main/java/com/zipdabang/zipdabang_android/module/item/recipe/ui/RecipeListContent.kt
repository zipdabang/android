package com.zipdabang.zipdabang_android.module.item.recipe.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem

@Composable
fun RecipeListContent(
    items: LazyPagingItems<RecipeItem>,
    onLikeClick: (Int) -> Unit,
    onScrapClick: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Log.d("Error", items.loadState.toString())
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
                        user = recipeItem.owner,
                        thumbnail = recipeItem.thumbnailUrl,
                        date = recipeItem.createdAt,
                        likes = recipeItem.likes,
                        comments = recipeItem.comments,
                        isLikeSelected = recipeItem.isLiked,
                        isScrapSelected = recipeItem.isScrapped,
                        onLikeClick = onLikeClick,
                        onScrapClick = onScrapClick,
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