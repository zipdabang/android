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
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    recipeViewModel : RecipeMainViewModel = hiltViewModel()
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

                    items(previewList) {
                            item ->
                        var isLiked by rememberSaveable { mutableStateOf(item.isLiked) }
                        var isScraped by rememberSaveable { mutableStateOf(item.isScrapped) }
                        var likes by rememberSaveable { mutableStateOf(item.likes) }
                        RecipeCard(
                            recipeId = item.recipeId,
                            title = item.recipeName,
                            user = item.nickname,
                            thumbnail = item.thumbnailUrl,
                            date = item.createdAt,
                            likes = likes,
                            comments = item.comments,
                            isLikeSelected = isLiked,
                            isScrapSelected = isScraped,
                            onLikeClick = {
                                recipeViewModel.toggleLike(item.recipeId)
                                isLiked = !isLiked
                                item.isLiked = !item.isLiked
                                if (isLiked) {
                                    item.likes += 1
                                } else {
                                    item.likes -= 1
                                }
                                likes = item.likes
                            },
                            onScrapClick = {
                               recipeViewModel.toggleScrap(item.recipeId)
                                isScraped = !isScraped
                            },
                            onItemClick = {
                                onRecipeItemClick(item.recipeId)
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


