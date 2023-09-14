package com.zipdabang.zipdabang_android.module.search.ui

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.navigation.SharedScreen
import com.zipdabang.zipdabang_android.module.item.goods.ui.GoodsCard
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.market.ui.ShimmeringMarketItem
import com.zipdabang.zipdabang_android.module.search.data.SearchCategory
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import kotlinx.coroutines.launch

@Composable
fun SearchCategoryScreen(
    navController: NavController,
    onRecipeItemClick : (Int) -> Unit,
   searchViewModel: SearchCategoryViewModel = hiltViewModel()

){
    val allItems = searchViewModel.getSearchRecipeCategoryItems.collectAsLazyPagingItems()
    val context = LocalContext.current
    val categoryId = searchViewModel.categoryId
    var isLoading by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
                AppBarSignUp(
                    navigationIcon = com.kakao.sdk.friend.R.drawable.material_ic_keyboard_arrow_left_black_24dp,
                    onClickNavIcon = {},
                    centerText = SearchCategory.values().get(categoryId.value-1).categoryName
                )


        },
        content= {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(top = it.calculateTopPadding(),start= 4.dp, end= 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    allItems.itemCount
                ) { index ->

                    if (allItems.loadState.refresh is LoadState.Loading || allItems.loadState.append is LoadState.Loading) {
                        if(isLoading) ShimmeringMarketItem()
                    } else {
                        RecipeCard(
                            recipeId = allItems[index]!!.recipeId,
                            title = allItems[index]!!.recipeName,
                            user = allItems[index]!!.nickname,
                            thumbnail = allItems[index]!!.thumbnailUrl,
                            date = allItems[index]!!.createdAt,
                            likes = allItems[index]!!.likes,
                            comments = allItems[index]!!.comments,
                            isLikeSelected = allItems[index]!!.isLiked,
                            isScrapSelected = allItems[index]!!.isScrapped,
                            onLikeClick = { TODO() },
                            onScrapClick = { TODO() },
                            onItemClick = {onRecipeItemClick(it)}
                        )
                    }
                }
            }

        })
}