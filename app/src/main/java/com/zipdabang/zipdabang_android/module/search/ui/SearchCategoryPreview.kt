package com.zipdabang.zipdabang_android.module.search.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeMainViewModel
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import com.zipdabang.zipdabang_android.ui.shimmeringEffect

@Composable
fun SearchCategoryPreview(
    title: String,
    previewList: List<SearchRecipe>,
    onRecipeItemClick : (Int) -> Unit,
    onClick: () -> Unit={},

){
   val mainViewModel = hiltViewModel<RecipeMainViewModel>()

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        SearchPreviewHeader(groupName = title, onClick = onClick )
        Spacer(modifier = Modifier.height(5.dp))
        if (previewList.isEmpty()) {
            NoSearchResult()
        } else {
            LazyRow(
                modifier = Modifier.padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                    items(previewList.size) {
                        RecipeCard(
                            recipeId = previewList[it].recipeId,
                            title = previewList[it].recipeName,
                            user = previewList[it].nickname,
                            thumbnail = previewList[it].thumbnailUrl,
                            date = previewList[it].createdAt,
                            likes = previewList[it].likes,
                            comments = previewList[it].comments,
                            isLikeSelected = previewList[it].isLiked,
                            isScrapSelected = previewList[it].isScrapped,
                            onLikeClick = { TODO() },
                            onScrapClick = { TODO() },
                            onItemClick = {
                                onRecipeItemClick(it)
                            }
                        )
                    }
                }
                }
            }


        }





@Composable
fun SearchCategoryPreviewLoading(
    title: String,
    onClick: () -> Unit={},
    ){
    val mainViewModel = hiltViewModel<RecipeMainViewModel>()

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        SearchPreviewHeader(groupName = title, onClick = onClick )
        Spacer(modifier = Modifier.height(5.dp))
            LazyRow(
                modifier = Modifier.padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(5) {

                    Box(modifier = Modifier
                            .width(160.dp)
                            .height(228.dp)
                            .shimmeringEffect()
                        )

            }
        }
    }


}


