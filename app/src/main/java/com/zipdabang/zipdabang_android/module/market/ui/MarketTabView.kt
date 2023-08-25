package com.zipdabang.zipdabang_android.module.market.ui


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.item.goods.RankItem
import com.zipdabang.zipdabang_android.module.item.goods.ui.HotItem
import com.zipdabang.zipdabang_android.ui.component.noRippleClickable
import com.zipdabang.zipdabang_android.ui.theme.MarketBrown


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MarketTabView(
    modifier : Modifier,
    categoryList : List<String>,
    onTabSelected: (selectedIndex : Int) -> Unit
){
    var selectedTabIndex by remember{
        mutableStateOf(0)
    }
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        divider = {},
        indicator = {},
        edgePadding = 0.dp
    ) {
        categoryList.forEachIndexed { index, category ->
            Tab(
               selected = selectedTabIndex == index,
               onClick = {
                   selectedTabIndex = index
                   onTabSelected(index)
               },
                selectedContentColor = Color.Transparent,
                modifier = Modifier.noRippleClickable {  }
            ){
                if(selectedTabIndex == index){

                Box(
                    Modifier
                        .width(68.dp)
                        .height(32.dp)
                        .background(color = MarketBrown, shape = RoundedCornerShape(size = 50.dp))
                        .padding(start = 20.dp, top = 4.dp, end = 20.dp, bottom = 3.dp)
                        .noRippleClickable { }
                )
                    {

                        Text(text = category ,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                            fontWeight = FontWeight(500),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier= Modifier.noRippleClickable {  }
                        )
                    }

                }else{
                    Text(text = category ,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                        fontWeight = FontWeight(500),
                        color = MarketBrown,
                        textAlign = TextAlign.Center,
                    )

                }


            }
        }
        
    }


}

@Composable
fun MarketRankItem(
    categoryRankList : List<RankItem>,
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        categoryRankList.forEachIndexed { index, item ->
            HotItem(
                rank = (index+1).toString(),
                imageUrl = item.imageUrl,
                marketName = item.marketName,
                productName = item.productName,
                price = item.price
            )
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun MarketTabPreview(){
    val categoryList = listOf("음료","재료","장비","굿즈","키트","전체")
    //MarketTabView(categoryList = categoryList, onTabSelected = {})
}