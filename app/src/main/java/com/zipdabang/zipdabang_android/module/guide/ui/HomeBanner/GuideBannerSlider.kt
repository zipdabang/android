package com.zipdabang.zipdabang_android.module.guide.ui.HomeBanner

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.zipdabang.zipdabang_android.ui.component.RectangleImage
import com.zipdabang.zipdabang_android.ui.theme.BannerGray
import com.zipdabang.zipdabang_android.ui.theme.White

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
fun GuideBannerSlider(
){
    val pagerState = com.google.accompanist.pager.rememberPagerState()
   Column(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
       horizontalAlignment = Alignment.CenterHorizontally
    ){
        HorizontalPager(
            count = 1,
            state= pagerState,
            verticalAlignment = Alignment.CenterVertically,
        ) {
               when(it) {
                   0 -> Box(Modifier.height(100.dp).fillMaxWidth()){
                       HomeGuideBanner_1()
                   }
               }
        }

       HorizontalPagerIndicator(
           pagerState = pagerState,
           modifier = Modifier
               .padding(10.dp)
           ,
           inactiveColor = Color(0x33262D31),
           activeColor = Color(0x80262D31),
           indicatorHeight = 4.dp,
           indicatorWidth = 4.dp
       )
    }


}