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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.zipdabang.zipdabang_android.common.TogglePreferenceException
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeListViewModel
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeListContent(
    items: LazyPagingItems<RecipeItem>,
    onItemClick: (Int) -> Unit,
    content: @Composable() () -> Unit,
) {

    val TAG = "RecipeListContent"

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
            modifier = Modifier
                .fillMaxWidth()
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

                    var isLiked by remember { mutableStateOf(recipeItem.isLiked) }
                    var isScraped by remember { mutableStateOf(recipeItem.isScrapped) }
                    var likes by remember { mutableStateOf(recipeItem.likes) }

                    val likeState = viewModel.toggleLikeResult.collectAsState()
                    val scrapState = viewModel.toggleScrapResult.collectAsState()

                    if (likeState.value.errorMessage != null
                        || scrapState.value.errorMessage != null) {
                        throw TogglePreferenceException
                    }

                    if (likeState.value.isLoading || likeState.value.isLoading) {
                        CircularProgressIndicator(color = ZipdabangandroidTheme.Colors.Strawberry)
                    }

                    RecipeCard(
                        recipeId = recipeItem.recipeId,
                        title = recipeItem.recipeName,
                        user = recipeItem.nickname,
                        thumbnail = recipeItem.thumbnailUrl,
                        date = recipeItem.createdAt,
                        likes = likes,
                        comments = recipeItem.comments ?: 0,
                        isLikeSelected = isLiked,
                        isScrapSelected = isScraped,
                        onLikeClick = { recipeId ->
                            Log.d("RecipeCard Status-before", "$isLiked")
                            try {
                                viewModel.toggleLike(recipeId)
                                recipeItem.isLiked = !recipeItem.isLiked
                                isLiked = recipeItem.isLiked
                                if (isLiked) {
                                    recipeItem.likes += 1
                                } else {
                                    recipeItem.likes -= 1
                                }
                                likes = recipeItem.likes
                            } catch (e: TogglePreferenceException) {
                                Log.e(TAG, "like toggle failure ${e.message}")
                            } catch (e: Exception) {
                                Log.e(TAG, "like toggle failure ${e.message}")
                            }
                        },
                        onScrapClick = { recipeId ->
                            try {
                                viewModel.toggleScrap(recipeId)
                                recipeItem.isScrapped = !recipeItem.isScrapped
                                isScraped = recipeItem.isScrapped
                            } catch (e: TogglePreferenceException) {
                                Log.e(TAG, "scrap toggle failure ${e.message}")
                            } catch (e: Exception) {
                                Log.e(TAG, "scrap toggle failure ${e.message}")
                            }
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