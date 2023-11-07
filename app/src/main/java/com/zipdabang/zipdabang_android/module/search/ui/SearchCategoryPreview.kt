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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeMainViewModel
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import com.zipdabang.zipdabang_android.ui.component.LoginRequestDialog
import com.zipdabang.zipdabang_android.ui.shimmeringEffect
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch

@Composable
fun SearchCategoryPreview(
    title: String,
    previewList: List<SearchRecipe>,
    onRecipeItemClick : (Int) -> Unit,
    onClick: () -> Unit={},
    onGoToLogin : () -> Unit,
    onLikeClick : (Int) -> Deferred<Boolean>,
    onScrapClick : (Int)-> Deferred<Boolean>,
    showSnackBar : (String) -> Unit,
    protoDataViewModel: ProtoDataViewModel = hiltViewModel(),
){
    val isLoginDialogShow = remember{
        mutableStateOf(false)
    }
    val currentPlatform = protoDataViewModel.tokens.collectAsState(
        initial = Token(
            null,
            null,
            null,
            CurrentPlatform.NONE,
            null,
            null
        )
    )

    val scope = rememberCoroutineScope()
    LoginRequestDialog(
        showDialog = isLoginDialogShow.value,
        setShowDialog ={
            isLoginDialogShow.value = it
        }
    ) {
        onGoToLogin()
    }
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
                                if(currentPlatform.value.platformStatus != CurrentPlatform.TEMP) {
                                    scope.launch {

                                        val isSuccess =
                                            onLikeClick(item.recipeId).await()
                                        if (isSuccess) {
                                            isLiked = !isLiked
                                            item.isLiked = !item.isLiked
                                            if (isLiked) {
                                                item.likes += 1
                                            } else {
                                                item.likes -= 1
                                            }
                                            likes = item.likes
                                        }else{
                                            showSnackBar("좋아요를 누를 수 없습니다.")
                                        }
                                    }
                                }else{
                                    isLoginDialogShow.value = true
                                }
                        }
                            ,
                            onScrapClick = {
                                if(currentPlatform.value.platformStatus != CurrentPlatform.TEMP) {
                                    scope.launch {
                                        val isSuccess =
                                            onScrapClick(item.recipeId).await()
                                        if (isSuccess) {
                                            isScraped = !isScraped
                                        } else {
                                            showSnackBar("레시피를 스크랩할 수 없습니다.")

                                        }
                                    }
                                }
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


