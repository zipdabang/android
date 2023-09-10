package com.zipdabang.zipdabang_android.module.recipes.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TogglePreferenceException
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCardLoading
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipePreviewState
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeMainViewModel
import com.zipdabang.zipdabang_android.ui.component.GroupHeader
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RecipePreviewByOwner(
    title: OwnerType,
    state: RecipePreviewState,
    onOwnerTypeClick: (String) -> Unit,
    onRecipeClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    onScrapClick: (Int) -> Unit,
    likeState: StateFlow<PreferenceToggleState>,
    scrapState: StateFlow<PreferenceToggleState>
) {
    val TAG = "RecipePreviewByOwner"

    GroupHeader(
        groupName = title.type,
        formerHeaderStrawberry = title.title,
        latterHeaderChoco = stringResource(R.string.group_header_recipe_of),
        onClick = onOwnerTypeClick
    )

    Spacer(modifier = Modifier.height(6.dp))

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (state.isLoading) {
            items(5) {
                RecipeCardLoading()
            }
        } else {
            state.data?.let { recipeItem ->
                items(recipeItem) { recipeInfo ->

                    var isLiked by remember { mutableStateOf(recipeInfo.isLiked) }
                    var isScraped by remember { mutableStateOf(recipeInfo.isScrapped) }
                    var likes by remember { mutableStateOf(recipeInfo.likes) }

                    val likeProcedure = likeState.collectAsState()
                    val scrapProcedure = scrapState.collectAsState()

                    if (likeProcedure.value.isLoading || scrapProcedure.value.isLoading) {
                        CircularProgressIndicator(color = ZipdabangandroidTheme.Colors.Strawberry)
                    }

                    if (likeProcedure.value.errorMessage != null
                        || scrapProcedure.value.errorMessage != null) {
                        throw TogglePreferenceException
                    }

                    RecipeCard(
                        recipeId = recipeInfo.recipeId,
                        title = recipeInfo.recipeName,
                        user = recipeInfo.nickname,
                        thumbnail = recipeInfo.thumbnailUrl,
                        date = recipeInfo.createdAt,
                        likes = likes,
                        comments = recipeInfo.comments ?: 0,
                        isLikeSelected = isLiked,
                        isScrapSelected = isScraped,
                        onLikeClick = { recipeId ->
                            Log.d("RecipeCard Status-before", "$isLiked")
                            try {
                                onLikeClick(recipeId)
                                recipeInfo.isLiked = !recipeInfo.isLiked
                                isLiked = recipeInfo.isLiked
                                if (isLiked) {
                                    recipeInfo.likes += 1
                                } else {
                                    recipeInfo.likes -= 1
                                }
                                likes = recipeInfo.likes
                            } catch (e: TogglePreferenceException) {
                                Log.e(TAG, "like toggle failure ${e.message}")
                            } catch (e: Exception) {
                                Log.e(TAG, "like toggle failure ${e.message}")
                            }
                        },
                        onScrapClick = { recipeId ->
                            try {
                                onScrapClick(recipeId)
                                recipeInfo.isScrapped = !recipeInfo.isScrapped
                                isScraped = recipeInfo.isScrapped
                            } catch (e: TogglePreferenceException) {
                                Log.e(TAG, "scrap toggle failure ${e.message}")
                            } catch (e: Exception) {
                                Log.e(TAG, "scrap toggle failure ${e.message}")
                            }
                        },
                        onItemClick = onRecipeClick
                    )
                }

                item {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

}