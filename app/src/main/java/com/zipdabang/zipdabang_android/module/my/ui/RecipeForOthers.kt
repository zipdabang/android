package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyForOthersViewModel
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeMainViewModel
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.component.GroupHeader
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeForOthers(
    nickname : String,
    viewModel : MyForOthersViewModel = hiltViewModel(),
    recipeMainViewModel : RecipeMainViewModel = hiltViewModel()
    ){

    val profileState = viewModel.profileState
    val recipePreviewState = viewModel.otherRecipePreviewState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {


        GroupHeader(
            groupName = "다른 사람 레시피",
            formerHeaderStrawberry = nickname ,
            latterHeaderChoco = "의 레시피",
            onClick = {}
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(recipePreviewState.value.recipeList) {
                item ->
                var isLiked by rememberSaveable { mutableStateOf(item.isLiked) }
                var isScraped by rememberSaveable { mutableStateOf(item.isScrapped) }
                var likes by rememberSaveable { mutableStateOf(item.likes) }
                RecipeCard(
                    recipeId = item.recipeId,
                    title =item.recipeName ,
                    user = item.nickname,
                    thumbnail = item.thumbnailUrl,
                    date = item.createdAt,
                    likes = likes,
                    comments = item.comments,
                    isLikeSelected = isLiked,
                    isScrapSelected = isScraped,
                    onLikeClick = {
                        recipeMainViewModel.toggleLike(item.recipeId)
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
                        recipeMainViewModel.toggleScrap(item.recipeId)
                        isScraped = !isScraped
                    },
                    onItemClick = {}
                )

            }

        }



    }

}