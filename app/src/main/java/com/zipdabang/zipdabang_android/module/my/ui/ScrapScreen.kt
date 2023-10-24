package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ScrapScreen(
    onClickBack: () -> Unit,
    onRecipeItemClick: (Int) -> Unit,
    viewModel: MyRecipesViewModel = hiltViewModel()
) {
    val scrapRecipeItems = viewModel.scrapRecipeItems.collectAsLazyPagingItems()
    val loadingState = rememberUpdatedState(scrapRecipeItems.loadState)

    LaunchedEffect(key1 = true) {
        viewModel.getScrapRecipeItems()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack() },
                centerText = stringResource(id = R.string.my_scrap)
            )
        },
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
            else if (scrapRecipeItems.itemCount == 0) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "스크랩 한 레시피가 없습니다.",
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
                    items(scrapRecipeItems.itemCount) {
                        var isLiked by rememberSaveable { mutableStateOf(scrapRecipeItems[it]!!.isLiked) }
                        var isScrapped by rememberSaveable { mutableStateOf(scrapRecipeItems[it]!!.isScrapped) }
                        var likes by rememberSaveable { mutableStateOf(scrapRecipeItems[it]!!.likes) }

                        RecipeCard(
                            recipeId = scrapRecipeItems[it]!!.recipeId,
                            title = scrapRecipeItems[it]!!.recipeName,
                            user = scrapRecipeItems[it]!!.nickname,
                            thumbnail = scrapRecipeItems[it]!!.thumbnailUrl,
                            date = scrapRecipeItems[it]!!.createdAt,
                            likes = likes,
                            comments = scrapRecipeItems[it]!!.comments,
                            isLikeSelected = isLiked,
                            isScrapSelected = isScrapped,
                            onLikeClick = { recipeId ->
                                CoroutineScope(Dispatchers.Main).launch {
                                    withContext(Dispatchers.IO){
                                        viewModel.postLike(recipeId)
                                    }

                                    scrapRecipeItems[it]!!.isLiked = !scrapRecipeItems[it]!!.isLiked
                                    isLiked = scrapRecipeItems[it]!!.isLiked

                                    if(isLiked){
                                        scrapRecipeItems[it]!!.likes += 1
                                    }
                                    else {
                                        scrapRecipeItems[it]!!.likes -= 1
                                    }
                                    likes =  scrapRecipeItems[it]!!.likes
                                }
                            },
                            onScrapClick = { recipeId ->
                                CoroutineScope(Dispatchers.Main).launch {
                                    withContext(Dispatchers.IO) {
                                        viewModel.postScrap(recipeId)
                                    }
                                    scrapRecipeItems[it]!!.isScrapped = !scrapRecipeItems[it]!!.isScrapped
                                    isScrapped = scrapRecipeItems[it]!!.isScrapped
                                }
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
