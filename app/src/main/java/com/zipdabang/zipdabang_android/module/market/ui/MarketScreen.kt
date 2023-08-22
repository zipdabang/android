package com.zipdabang.zipdabang_android.module.market.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.zipdabang.zipdabang_android.module.item.goods.ui.GoodsCard
import com.zipdabang.zipdabang_android.module.item.goods.ui.MarketCategory
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.Banner
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.theme.MarketRecentBrown
import kotlinx.coroutines.launch

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
                    Column(
                        modifier = Modifier.padding(top = it.calculateTopPadding(),bottom = it.calculateBottomPadding())
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

                        Row(
                            modifier =Modifier
                                .padding(horizontal = 8.dp, vertical = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(text = "내가 최근 봤던 아이템",
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontFamily = FontFamily(Font(R.font.cafe24ohsquareair)),
                                fontWeight = FontWeight(300),
                                color = MarketRecentBrown
                            )
                            Icon(painter= painterResource(id = R.drawable.market_arrow_small),contentDescription = null,
                                tint = MarketRecentBrown)
                        }

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
                                        GoodsCard(
                                            image = item.productImageUrl,
                                            isBasket = item.isInBasket,
                                            isFavorite = item.isLiked,
                                            title = item.productName,
                                            price = item.price,
                                            star = item.productScore,
                                            star_users = "1"
                                        )
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


@Composable
fun MarketScreenn(
){
    val empty =true
    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val images = listOf(
        "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
        "http://www.econovill.com/news/photo/201504/241659_32000_5414.jpg",
        "https://file.newswire.co.kr/data/datafile2/thumb_480/2019/06/3554238800_20190620153646_2171882603.jpg"
    )
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
                    Column(
                        modifier = Modifier.padding(top = it.calculateTopPadding(),bottom = it.calculateBottomPadding())
                    ) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)) {
                            Banner(images)
                        }
                        Row(
                            modifier = Modifier.padding(start = 8.dp,end = 8.dp, top= 20.dp, bottom = 10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            MarketCategory(category = "음료", imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/", onClick = { TODO() })
                            MarketCategory(category = "재료", imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/", onClick = { TODO() })
                            MarketCategory(category ="장비", imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/", onClick = { TODO() })
                            MarketCategory(category = "굿즈", imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/", onClick = { TODO() })
                            MarketCategory(category = "키트", imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/", onClick = { TODO() })
                            MarketCategory(category = "장비", imageUrl ="https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/", onClick = { TODO() })
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


                        Row(
                            modifier =Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween

                        ){
                            Text(text = "내가 최근 봤던 아이템",
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontFamily = FontFamily(Font(R.font.cafe24ohsquareair)),
                                fontWeight = FontWeight(300),
                                color = MarketRecentBrown
                            )
                            Icon(painter= painterResource(id = R.drawable.market_arrow_small),contentDescription = null,
                                tint = MarketRecentBrown)
                        }

                        if(!empty){
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(224.dp)
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
                                modifier= Modifier.padding(horizontal = 4.dp),
                            ){
                                items(3){
                                        item ->

                                    GoodsCard(
                                        image = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
                                        isBasket = false,
                                        isFavorite = false,
                                        title = "우유우유우유",
                                        price ="20000원",
                                        star = 3,
                                        star_users = "1"
                                    )
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


@Preview
@Composable
fun marketPreview() {
    MarketScreenn()
}