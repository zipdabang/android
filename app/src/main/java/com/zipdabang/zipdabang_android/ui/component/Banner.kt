package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.*
import com.zipdabang.zipdabang_android.ui.theme.BannerGray
import com.zipdabang.zipdabang_android.ui.theme.White
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun Banner(
    images : List<String>,
){
    val pagerState = com.google.accompanist.pager.rememberPagerState()
    Box(
     modifier = Modifier.fillMaxSize()
    ){
    HorizontalPager(
        count = 3,
        state= pagerState,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        position -> RectangleImage(
        imageUrl = images[position],
        contentDescription = "banner image"
        )
    }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier =  Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp),
            inactiveColor = BannerGray,
            activeColor = White
        )
    }


}


@Preview
@Composable
fun BannerPreview(){
    val images = listOf(
        "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/",
        "http://www.econovill.com/news/photo/201504/241659_32000_5414.jpg",
        "https://file.newswire.co.kr/data/datafile2/thumb_480/2019/06/3554238800_20190620153646_2171882603.jpg"
    )

    Column(
    ) {
        Box(modifier = Modifier.size(width=300.dp,height=200.dp)) {
            Banner(images)
        }
    }
}