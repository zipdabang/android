package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.*
import com.zipdabang.zipdabang_android.module.recipes.data.banner.BannerImageItem
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
        count = images.size,
        state= pagerState,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        position -> RectangleImage(
        imageUrl = images[position],
        contentDescription = "banner image"
        )
    }
        Box(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = 10.dp, end = 16.dp)
            .size(width = 43.dp, height = 22.dp)

        ){
            BannerIndicator(
                currentPage = pagerState.currentPage + 1 ,
                totalPage = images.size
            )
        }

    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun BannerForRecipe(
    banners: List<BannerImageItem>,
    onClick: (String) -> Unit
){
    val pagerState = com.google.accompanist.pager.rememberPagerState()
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        HorizontalPager(
            modifier = Modifier,
            count = banners.size,
            state= pagerState,
            verticalAlignment = Alignment.CenterVertically,
        ) { position ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onClick(banners[position].searchKeyword)
                    }
            ) {
                RectangleImage(
                    imageUrl = banners[position].imageUrl,
                    contentDescription = "banner image"
                )
            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp),
            inactiveColor = BannerGray,
            activeColor = White
        )
    }


}

@Composable
fun BannerLoading() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        // the lightest color should be in the middle
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.35f)
    )

    // animate shimmer as long as we want
    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier
            .fillMaxSize()
            .background(brush))
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
        Box(modifier = Modifier
            .width(500.dp)
            .height(200.dp)) {
            Banner(images)
        }
    }
}

@Preview
@Composable
fun BannerLoadingPreview() {
    BannerLoading()
}