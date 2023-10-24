package com.zipdabang.zipdabang_android.module.item.recipe.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.zipdabang.zipdabang_android.common.TogglePreferenceException
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSort
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSubtitleState
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.ui.component.SortSpinner
import com.zipdabang.zipdabang_android.ui.component.Spinner
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeListContent(
    items: LazyPagingItems<RecipeItem>,
    onItemClick: (Int) -> Unit,
    total: String,
    sortList: List<RecipeSort>,
    onSortChange: (String) -> Unit,
    category: RecipeSubtitleState,
    likeState: PreferenceToggleState,
    scrapState: PreferenceToggleState,
    onToggleLike: (Int, Int?, String?) -> Unit,
    onToggleScrap: (Int, Int?, String?) -> Unit,
    lazyGridState: LazyGridState,
    content: @Composable() () -> Unit,
) {

    val TAG = "RecipeListContent"

/*    LaunchedEffect(key1 = items.loadState) {
        if (items.loadState.refresh is LoadState.Loading) {

            Log.d("RecipeList - content", "loading...")
        } else if (items.loadState.refresh is LoadState.Error) {
            Log.d("RecipeList - content", (items.loadState.refresh as LoadState.Error).error.message ?: "unexpected error")
        }
    }*/

    val optionList = sortList.map {
        it.text
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Log.d("Error", items.loadState.toString())
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            content()
            Row(
                modifier = Modifier.fillMaxWidth().height(56.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = "${total}개의 레시피",
                    style = ZipdabangandroidTheme.Typography.fourteen_300
                        .copy(
                            color = Color(0x88262D31)
                        )
                )

                SortSpinner(
                    modifier = Modifier.weight(4f),
                    optionList = optionList,
                    onItemChange = onSortChange
                )
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                all = 12.dp
            ),
            state = lazyGridState,
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

                    var isLiked by rememberSaveable { mutableStateOf(recipeItem.isLiked) }
                    var isScraped by rememberSaveable { mutableStateOf(recipeItem.isScrapped) }
                    var likes by rememberSaveable { mutableStateOf(recipeItem.likes) }

                    if (likeState.errorMessage != null
                        || scrapState.errorMessage != null) {
                        throw TogglePreferenceException
                    }

                    if (likeState.isLoading || likeState.isLoading) {
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
                                onToggleLike(recipeId, category.categoryId, category.ownerType)
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
                                onToggleScrap(recipeId, category.categoryId, category.ownerType)
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

            if (items.loadState.refresh is LoadState.Loading && items.itemCount == 0) {
                items(count = 10) {
                    RecipeCardLoading()
                }
            }
        }
    }
}