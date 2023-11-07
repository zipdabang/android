package com.zipdabang.zipdabang_android.module.search.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.market.ui.ShimmeringMarketItem
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeMainViewModel
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchCategory
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchSortList
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.LoginRequestDialog
import com.zipdabang.zipdabang_android.ui.component.Spinner
import com.zipdabang.zipdabang_android.ui.shimmeringEffect
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch

@Composable
fun SearchCategoryScreen(
    onRecipeItemClick : (Int) -> Unit,
    onGoToBack : () -> Unit,
    onGoToLogin : () -> Unit,
    onLikeClick : (Int) -> Deferred<Boolean>,
    onScrapClick : (Int)-> Deferred<Boolean>,
    showSnackBar : (String) -> Unit,
    protoDataViewModel: ProtoDataViewModel = hiltViewModel(),
    searchViewModel: SearchCategoryViewModel = hiltViewModel(),

    ){
    val allItems = searchViewModel.recipeList.collectAsLazyPagingItems()
    
    val categoryId = searchViewModel.categoryId

    val searchState = searchViewModel.searchState

    val scope = rememberCoroutineScope()

    var isLoading by remember {
        mutableStateOf(false)
    }
    when(allItems.loadState.refresh)
    {
        is LoadState.Loading -> {
            isLoading = true
            Log.e("isLoading","true")
        }
        else -> {
            isLoading = false
            Log.e("isLoading","false")

        }
    }
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


    LoginRequestDialog(
        showDialog = isLoginDialogShow.value,
        setShowDialog ={
            isLoginDialogShow.value = it
        }
    ) {
        onGoToLogin()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
                AppBarSignUp(
                    navigationIcon = com.kakao.sdk.friend.R.drawable.material_ic_keyboard_arrow_left_black_24dp,
                    onClickNavIcon = {
                        onGoToBack()
                    },
                    centerText = SearchCategory.values().get(categoryId.value-1).categoryName
                )


        },
        content= {
            val searchSortList = listOf(SearchSortList.Latest.sortName,SearchSortList.Follow.sortName,SearchSortList.Popular.sortName)


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(
                        top = it.calculateTopPadding(),
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            ){
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {

                    Text("${searchState.value.count}개의 레시피",
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Choco)
                    Box(
                        modifier = Modifier.width(128.dp)
                    ) {
                        Spinner(
                            optionList = searchSortList,
                            onItemChange = {
                                if(it=="최신순")searchViewModel.order.value = SearchSortList.Latest.sortRequest
                                else if(it=="팔로우순")searchViewModel.order.value = SearchSortList.Follow.sortRequest
                                else searchViewModel.order.value = SearchSortList.Popular.sortRequest
                                searchViewModel.getSearchRecipeList()
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(start= 4.dp, end= 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!isLoading) {
                    items(
                        allItems.itemCount
                    ) { index ->
                        var isLiked by rememberSaveable { mutableStateOf(allItems[index]!!.isLiked) }
                        var isScrapped by rememberSaveable { mutableStateOf(allItems[index]!!.isScrapped) }
                        var likes by rememberSaveable { mutableStateOf(allItems[index]!!.likes) }
                        RecipeCard(
                            recipeId = allItems[index]!!.recipeId,
                            title = allItems[index]!!.recipeName,
                            user = allItems[index]!!.nickname,
                            thumbnail = allItems[index]!!.thumbnailUrl,
                            date = allItems[index]!!.createdAt,
                            likes = likes,
                            comments = allItems[index]!!.comments,
                            isLikeSelected = isLiked,
                            isScrapSelected = isScrapped,
                            onLikeClick = {
                                if(currentPlatform.value.platformStatus!=CurrentPlatform.TEMP ) {
                                    scope.launch {
                                        val isSuccess =
                                            onLikeClick(allItems[index]!!.recipeId).await()
                                        if (isSuccess) {
                                            isLiked = !isLiked
                                            if (isLiked) {
                                                allItems[index]!!.likes += 1
                                            } else {
                                                allItems[index]!!.likes -= 1
                                            }
                                            likes = allItems[index]!!.likes
                                        } else {
                                            showSnackBar("좋아요를 누를 수 없습니다.")
                                        }
                                    }
                                }else{
                                    isLoginDialogShow.value = true
                                }
                                          },
                            onScrapClick = {
                                if(currentPlatform.value.platformStatus!=CurrentPlatform.TEMP ) {
                                    scope.launch {
                                        val isSuccess =
                                            onScrapClick(allItems[index]!!.recipeId).await()
                                        if (isSuccess) {
                                            isScrapped = !isScrapped
                                        }else{
                                            showSnackBar("레시피를 스크랩할 수 없습니다.")
                                        }
                                    }
                                }else{
                                    isLoginDialogShow.value = true
                                }
                            },
                            onItemClick = { onRecipeItemClick(it) }
                        )
                    }

            }else{
                items(10){
                    Box(modifier = Modifier
                        .width(160.dp)
                        .height(228.dp)
                        .shimmeringEffect()
                    )

                }

            }
            }




            }

        })

}


