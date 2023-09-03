package com.zipdabang.zipdabang_android.module.market.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.item.goods.ui.GoodsCard
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.component.SearchBar
import com.zipdabang.zipdabang_android.ui.theme.MarketBrown
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
@Composable
fun CategoryMarketScreen(
    categoryId : Int,
    marketViewModel: MarketCategoryViewModel = hiltViewModel(),
) {

    val title = when(categoryId){
         0 -> "전체"
         1 -> "음료"
         2 -> "재료"
         3 -> "장비"
         4 -> "굿즈"
         5 -> "키트"
        else -> { null }
    }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    marketViewModel.setCategoryId(categoryId)
    val allItems = marketViewModel.getMarketCategoryItems.collectAsLazyPagingItems()
    val context = LocalContext.current

    LaunchedEffect(key1 = allItems.loadState) {
        if(allItems.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (allItems.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    Log.e("viewmodel_screen","id")
                    if (title != null) {
                        AppBarHome(
                            endIcon1 = R.drawable.ic_topbar_search,
                            endIcon2 = R.drawable.ic_topbar_menu,
                            onClickEndIcon1 = {},
                            onClickEndIcon2 = { scope.launch { drawerState.open() } },
                            centerText = "마켓"
                        )
                    }
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                content = {
                    LazyColumn(modifier = Modifier.padding(top = it.calculateTopPadding())){
                        item {
                            Column(
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {

                                SearchBar(hintText = "찾는 상품을 검색해보세요")

                                if (title != null) {
                                    Text(
                                        text = title,
                                        fontSize = 16.sp,
                                        lineHeight = 24.sp,
                                        fontFamily = FontFamily(Font(R.font.cafe24ohsquareair)),
                                        fontWeight = FontWeight(300),
                                        color = MarketBrown,
                                        modifier = Modifier.padding(vertical = 10.dp)
                                    )
                                }
                            }
                        }

                        item {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)){
                                items(
                                   allItems.itemCount
                                ) {
                                        index ->
                                    if (allItems.loadState.refresh is LoadState.Loading || allItems.loadState.append is LoadState.Loading) {
                                        ShimmeringMarketItem()
                                    }
                                    else{
                                        GoodsCard(
                                            image = allItems[index]!!.productImageUrl,
                                            isBasket =allItems[index]!!.isInBasket ,
                                            isFavorite =allItems[index]!!.isLiked ,
                                            title =allItems[index]!!.productName ,
                                            price = allItems[index]!!.price,
                                            star =allItems[index]!!.productScore ,
                                            star_users ="3",
                                            {},
                                            {}
                                        )
                                    }
                                }

                            }
                        }

                    }

                }
            )
        },
        drawerState = drawerState
    )

}

@OptIn(ExperimentalPagingApi::class)
@Composable
@Preview
fun marketCategoryPreview(){
    CategoryMarketScreen(categoryId = 2)
}