package com.zipdabang.zipdabang_android.module.item.recipe.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch

@Composable
fun RecipeListContent(
    items: LazyPagingItems<RecipeItem>,
    onItemClick: (Int) -> Unit,
    total: String,
    sortList: List<RecipeSort>,
    onSortChange: (String) -> Unit,
    checkLoggedIn: () -> Boolean,
    onToggleLike: (Int) -> Deferred<Boolean>,
    onToggleScrap: (Int) -> Deferred<Boolean>,
    lazyGridState: LazyGridState,
    showSnackbar: (String) -> Unit,
    content: @Composable() () -> Unit,
) {

    val TAG = "RecipeListContent"

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = items.loadState.refresh) {
        if (items.loadState.refresh is LoadState.Loading) {
            Log.d("RecipeList - content", "loading...")
        } else if (items.loadState.refresh is LoadState.Error) {
            Log.d("RecipeList - content", (items.loadState.refresh as LoadState.Error).error.message ?: "unexpected error")
        }
    }

    val optionList = sortList.map {
        it.text
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Log.d("Error", items.loadState.toString())

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                all = 16.dp
            ),
            state = lazyGridState,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item(
                span = {
                    GridItemSpan(2)
                }
            ) {
                content()
            }

            item(
                span = {
                    GridItemSpan(2)
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(6f),
                        text = if (total == "-1") "레시피 불러오는 중..." else "${total}개의 레시피",
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

            if (items.loadState.refresh is LoadState.Loading) {
                items(count = 10) {
                    RecipeCardLoading()
                }
            } else {
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
                                    val isLoggedIn = checkLoggedIn()
                                    if (isLoggedIn) {
                                        scope.launch {
                                            val isSuccessful = onToggleLike(recipeId).await()
                                            if (isSuccessful) {
                                                recipeItem.isLiked = !recipeItem.isLiked
                                                isLiked = recipeItem.isLiked
                                                if (isLiked) {
                                                    recipeItem.likes += 1
                                                } else {
                                                    recipeItem.likes -= 1
                                                }
                                                likes = recipeItem.likes
                                            } else {
                                                showSnackbar("레시피에 좋아요를 누를 수 없습니다.")
                                            }
                                        }

                                    }

                                } catch (e: TogglePreferenceException) {
                                    Log.e(TAG, "like toggle failure ${e.message}")
                                } catch (e: Exception) {
                                    Log.e(TAG, "like toggle failure ${e.message}")
                                }
                            },
                            onScrapClick = { recipeId ->
                                try {
                                    val isLoggedIn = checkLoggedIn()
                                    if (isLoggedIn) {
                                        scope.launch {
                                            val isSuccessful = onToggleScrap(recipeId).await()
                                            if (isSuccessful) {
                                                recipeItem.isScrapped = !recipeItem.isScrapped
                                                isScraped = recipeItem.isScrapped
                                            } else {
                                                showSnackbar("레시피를 스크랩 할 수 없습니다.")
                                            }

                                        }
                                    }

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
            }

            item(
                span = {
                    GridItemSpan(2)
                }
            ) {
                if (items.loadState.append is LoadState.Loading) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(36.dp),
                            color = ZipdabangandroidTheme.Colors.Choco
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}