package com.zipdabang.zipdabang_android.module.market.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
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
import androidx.navigation.NavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.navigation.MarketScreen
import com.zipdabang.zipdabang_android.module.item.goods.RankItem
import com.zipdabang.zipdabang_android.module.item.goods.ui.GoodsCard
import com.zipdabang.zipdabang_android.module.item.goods.ui.HotItem
import com.zipdabang.zipdabang_android.module.item.goods.ui.MarketCategory
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.Banner
import com.zipdabang.zipdabang_android.ui.component.GroupHeader
import com.zipdabang.zipdabang_android.ui.component.GroupHeaderReversed
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.theme.MarketBrown
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MarketScreen(
    viewMdoel: RecentMarketViewMdoel = hiltViewModel(),
    navController: NavController
){
    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val state = viewMdoel.state.value


    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarHome(
                        endIcon1 = R.drawable.ic_topbar_search,
                        endIcon2 = R.drawable.ic_topbar_menu,
                        onClickEndIcon1 = {},
                        onClickEndIcon2 = { scope.launch { drawerState.open() } },
                        centerText = "집다방"
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                content = {
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .padding(
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
                            )
                            .verticalScroll(scrollState)
                    ) {
                        Banner(images = state.bannerList)
                        Row(
                            modifier = Modifier.padding(start = 8.dp,end = 8.dp, top= 20.dp, bottom = 10.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            MarketCategory(category = state.categoryList[0].name, imageUrl = state.categoryList[0].categoryImageUrl, onClick = { navController.navigate(MarketScreen.Category.route + 1) })
                            MarketCategory(category = state.categoryList[1].name, imageUrl = state.categoryList[1].categoryImageUrl, onClick = { navController.navigate(MarketScreen.Category.route + 2) })
                            MarketCategory(category = state.categoryList[2].name, imageUrl = state.categoryList[2].categoryImageUrl, onClick = { navController.navigate(MarketScreen.Category.route + 3) })
                            MarketCategory(category = state.categoryList[3].name, imageUrl = state.categoryList[3].categoryImageUrl, onClick = { navController.navigate(MarketScreen.Category.route + 4) })
                            MarketCategory(category = state.categoryList[4].name, imageUrl = state.categoryList[4].categoryImageUrl, onClick = { navController.navigate(MarketScreen.Category.route + 5) })
                            MarketCategory(category = state.categoryList[5].name, imageUrl = state.categoryList[5].categoryImageUrl, onClick = {navController.navigate(MarketScreen.Category.route +  0 ) })
                        }

                        Canvas(
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .padding(horizontal = 8.dp)
                        ){
                            drawLine(
                                color= Color(0xFFCDC6C3),
                                start = Offset(0f,0f),
                                end = Offset(size.width,0f)

                            )
                        }
                        GroupHeader(
                            groupName = "recent items",
                            formerHeaderStrawberry = "내가 최근 봤던",
                            latterHeaderChoco = "아이템",
                            onClick = { TODO()  }
                        )

                        if(state.recentProductList.isEmpty()){
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .background(color = Color.White)
                            ) {
                                Text(
                                    text = "앗! 최근 봤던 아이템이 없네요.한번 찾아볼까요?",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                                    fontWeight = FontWeight(300),
                                    color = Color(0xFF262D31),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 5.dp)
                                )
                            }
                        }else{
                            LazyRow(
                                contentPadding= PaddingValues(horizontal = 4.dp),
                            ){
                                itemsIndexed(state.recentProductList){
                                    index, item ->
                                    if(state.isLoading){
                                        ShimmeringMarketItem()
                                    }
                                    else {
                                        GoodsCard(
                                            image = item.productImageUrl,
                                            isBasket = item.isInBasket,
                                            isFavorite = item.isLiked,
                                            title = item.productName,
                                            price = item.price,
                                            star = item.productScore,
                                            star_users = "1",
                                            {},
                                            {}
                                        )
                                    }
                                }
                            }
                        }
                        
                        GroupHeaderReversed(
                            groupName = "hot item",
                            formerHeaderChoco = "카테고리별 ",
                            latterHeaderStrawberry = "인기 아이템",
                            onClick = { TODO() }
                        )
                        val categoryList = listOf("음료","재료","장비","굿즈","키트","전체")

                        MarketTabView(categoryList =categoryList , modifier = Modifier.padding(horizontal = 8.dp), onTabSelected = {})










                    }



                }
            )
        },
        drawerState = drawerState
    )

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MarketScreen_Test(){
    val empty = false
    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val images = listOf(
        "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
        "http://www.econovill.com/news/photo/201504/241659_32000_5414.jpg",
        "https://file.newswire.co.kr/data/datafile2/thumb_480/2019/06/3554238800_20190620153646_2171882603.jpg"
    )
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    var likechecked by remember {
        mutableStateOf(false)
    }
    var inBasket by remember {
        mutableStateOf(false)
    }
    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarHome(
                        endIcon1 = R.drawable.ic_topbar_search,
                        endIcon2 = R.drawable.ic_topbar_menu,
                        onClickEndIcon1 = {},
                        onClickEndIcon2 = { scope.launch { drawerState.open() } },
                        centerText = "집다방"
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black
            ) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .padding(
                            top = it.calculateTopPadding(),
                            bottom = it.calculateBottomPadding()
                        )
                        .verticalScroll(scrollState)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Banner(images)
                    }
                    Row(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 20.dp, bottom = 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        MarketCategory(
                            category = "음료",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            onClick = { })//navController.navigate(MarketScreen.Category.passCategoryId(1))})
                        MarketCategory(
                            category = "재료",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            onClick = { })//navController.navigate(MarketScreen.Category.passCategoryId(2))})
                        MarketCategory(
                            category = "장비",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            onClick = { })//navController.navigate(MarketScreen.Category.passCategoryId(3)) })
                        MarketCategory(
                            category = "굿즈",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            onClick = {}) //navController.navigate(MarketScreen.Category.passCategoryId(4))})
                        MarketCategory(
                            category = "키트",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            onClick = {}) //navController.navigate(MarketScreen.Category.passCategoryId(5))})
                        MarketCategory(
                            category = "장비",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            onClick = { })//navController.navigate(MarketScreen.Category.passCategoryId(0))})
                    }

                    Canvas(
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .padding(horizontal = 8.dp)
                    ) {
                        drawLine(
                            color = Color(0xFFCDC6C3),
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f)

                        )
                    }


                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        Text(
                            text = "내가 최근 봤던 아이템",
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontFamily = FontFamily(Font(R.font.cafe24ohsquareair)),
                            fontWeight = FontWeight(300),
                            color = MarketBrown
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.market_arrow_small),
                            contentDescription = null,
                            tint = MarketBrown
                        )
                    }

                    if (empty) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(horizontal = 8.dp)
                        ) {
                            NoRecentItem()
                        }
                    } else {
                        LazyRow(
                            modifier = Modifier.padding(horizontal = 4.dp),
                        ) {
                            items(3) { item ->

                                GoodsCard(
                                    image = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                                    isBasket = inBasket,
                                    isFavorite = likechecked,
                                    title = "우유우유우유",
                                    price = "20000원",
                                    star = 3,
                                    star_users = "1",
                                    {state -> inBasket = state},
                                    { state -> likechecked = state }
                                )
                            }
                        }
                    }

                    GroupHeaderReversed(
                        groupName = "hot item",
                        formerHeaderChoco = "카테고리별 ",
                        latterHeaderStrawberry = "인기 아이템",
                        onClick = { TODO() }
                    )
                    val categoryList = listOf("음료", "재료", "장비", "굿즈", "키트", "전체")

                    MarketTabView(
                        categoryList = categoryList,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onTabSelected = {
                            selectedTabIndex = it
                        })
                    Spacer(modifier = Modifier.height(10.dp))

                    val list = listOf(
                        RankItem(
                            rank = "1",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            marketName = "집다방",
                            productName = "레모네이드",
                            price = "3000원"
                        ),
                        RankItem(
                            rank = "2",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            marketName = "집다방",
                            productName = "레모네이드",
                            price = "3000원"
                        ),
                        RankItem(
                            rank = "3",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            marketName = "집다방",
                            productName = "레모네이드",
                            price = "3000원"
                        ),
                        RankItem(
                            rank = "4",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            marketName = "집다방",
                            productName = "레모네이드",
                            price = "3000원"
                        ),
                        RankItem(
                            rank = "5",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            marketName = "집다방",
                            productName = "레모네이드",
                            price = "3000원"
                        )
                    )
                    val list2 = listOf(
                        RankItem(
                            rank = "1",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            marketName = "집다방23",
                            productName = "레모네이드",
                            price = "3000원"
                        ),
                        RankItem(
                            rank = "2",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            marketName = "집다방333",
                            productName = "레모네이드3",
                            price = "322000원"
                        ),
                        RankItem(
                            rank = "3",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            marketName = "집다방33",
                            productName = "레모네이드",
                            price = "3000원"
                        ),
                        RankItem(
                            rank = "4",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            marketName = "집다방4",
                            productName = "레모네이드",
                            price = "3000원"
                        ),
                        RankItem(
                            rank = "5",
                            imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                            marketName = "집다방5",
                            productName = "레모네이드",
                            price = "3000원"
                        )
                    )
                    when (selectedTabIndex) {
                        0 -> MarketRankItem(categoryRankList = list)
                        1 -> MarketRankItem(categoryRankList = list2)
                    }


                }


            }
        },
        drawerState = drawerState
    )

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun marketPreview() {
    MarketScreen_Test()
}