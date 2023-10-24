package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCardLoading
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyRecipesViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.SearchBar
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun MyRecipeListScreen(
    nickname : String,
    onClickBack: () -> Unit,
    onRecipeItemClick: (Int) -> Unit,
    onClickMyrecipe : ()->Unit,
    showSnackBar: (String) -> Unit,
    viewModel: MyRecipesViewModel = hiltViewModel()
) {
    val completeRecipeWithImgItems = viewModel.completeRecipeWithImgItems.collectAsLazyPagingItems()
    val loadingState = rememberUpdatedState(completeRecipeWithImgItems.loadState)

    LaunchedEffect(key1 = true) {
        viewModel.getCompleteRecipeWithImgItems()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack() },
                centerText = nickname + "님의 레시피"
            )
        },
        bottomBar = {
            // 레시피 작성하기
            Row(
                modifier = Modifier.height(56.dp)
            ){
                Box(
                    modifier = Modifier
                        .background(ZipdabangandroidTheme.Colors.Strawberry)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .clickable(
                            onClick = { onClickMyrecipe() }
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text= stringResource(id = R.string.my_addnewrecipe),
                        color = Color.White,
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                    )
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = Color.White,
        ) {
            /*Box(
                modifier = Modifier.padding(16.dp, 10.dp, 16.dp,0.dp)
                    .background(Color.White)
            ){
                SearchBar(hintText = stringResource(id = R.string.my_searchbar_keyword))
            }*/
            if(loadingState.value.refresh is LoadState.Loading){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(8.dp, 10.dp, 8.dp, 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(8) {
                        RecipeCardLoading()
                    }
                }
            }
            else if (completeRecipeWithImgItems.itemCount == 0) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "제작해 본 레시피가 아직 없습니다",
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
            }
            else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(8.dp, 10.dp, 8.dp, 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(completeRecipeWithImgItems.itemCount) {
                        RecipeCard(
                            recipeId = completeRecipeWithImgItems[it]!!.recipeId,
                            title = completeRecipeWithImgItems[it]!!.recipeName,
                            user = completeRecipeWithImgItems[it]!!.nickname,
                            thumbnail = completeRecipeWithImgItems[it]!!.thumbnailUrl,
                            date = completeRecipeWithImgItems[it]!!.createdAt,
                            likes = completeRecipeWithImgItems[it]!!.likes,
                            comments = completeRecipeWithImgItems[it]!!.comments,
                            isLikeSelected = completeRecipeWithImgItems[it]!!.isLiked,
                            isScrapSelected = completeRecipeWithImgItems[it]!!.isScrapped,
                            onLikeClick = {
                                showSnackBar("본인 레시피에 좋아요를 누를 수 없습니다.")
                            },
                            onScrapClick = {
                                showSnackBar("본인 레시피를 스크랩 할 수 없습니다.")
                            },
                            onItemClick = {
                                onRecipeItemClick(it)
                            }
                        )
                    }
                }
            }

        }
    }
}