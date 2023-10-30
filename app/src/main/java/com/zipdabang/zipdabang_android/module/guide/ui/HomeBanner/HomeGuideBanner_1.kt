package com.zipdabang.zipdabang_android.module.guide.ui.HomeBanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.RectangleWithTwoLinesText
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun HomeGuideBanner_1(
    onGuide1Click : ()->Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE7D7C9), shape = ZipdabangandroidTheme.Shapes.large)
            .clickable {
                onGuide1Click()
            }
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start=15.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                val color = ZipdabangandroidTheme.Colors.Typo
                val typography = FontFamily(Font(R.font.kopubworlddotum_medium))

                Text(
                    text = "홈카페는 처음이신가요?",
                    color = color,
                    fontFamily = typography,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .offset(y = (-2).dp)
                )
                Text(
                    text = "홈카페를 위한 A to Z!",
                    color = color,
                    fontFamily = typography,
                    fontSize = 16.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }


        }

            Image(painter = painterResource(id = R.drawable.guide_banner1_icon),contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd).height(94.dp)
                    .width(94.dp)     .offset(x = (3).dp))


}
}

@Composable
@Preview
fun HomeBannerPreview(){
   // HomeGuideBanner_1()
}