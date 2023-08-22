package com.zipdabang.zipdabang_android.module.market.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.item.goods.ui.GoodsCard
import com.zipdabang.zipdabang_android.module.item.goods.ui.MarketCategory
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.Banner
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.component.SearchBar
import com.zipdabang.zipdabang_android.ui.theme.MarketRecentBrown
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
@Composable
fun CategoryMarketScreen(
//    viewModel: MarketCategoryViewModel = hiltViewModel(),
    categoryId : Int?
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

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
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
                                        color = MarketRecentBrown,
                                        modifier = Modifier.padding(vertical = 10.dp)
                                    )
                                }
                            }
                        }

                        items( 100){
                            Text("100")
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