package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyForOthersViewModel
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.component.GroupHeader
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeForOthers(
    nickname : String,
    viewModel : MyForOthersViewModel = hiltViewModel()
){

    val profileState = viewModel.profileState
    val recipePreviewState = viewModel.otherRecipePreviewState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp)
    ) {


        GroupHeader(
            groupName = "다른 사람 레시피",
            formerHeaderStrawberry = nickname ,
            latterHeaderChoco = "의 레시피",
            onClick = {}
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            items(recipePreviewState.value.recipeList) {
                RecipeCard(
                    recipeId = it.recipeId,
                    title =it.recipeName ,
                    user = it.nickname,
                    thumbnail = it.thumbnailUrl,
                    date = it.createdAt,
                    likes = it.likes,
                    comments = it.comments,
                    isLikeSelected = it.isLiked,
                    isScrapSelected = it.isScrapped,
                    onLikeClick = {},
                    onScrapClick = {},
                    onItemClick = {}
                )

            }

        }



    }

}