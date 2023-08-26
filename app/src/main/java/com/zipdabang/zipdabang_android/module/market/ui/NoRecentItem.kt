package com.zipdabang.zipdabang_android.module.market.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.zipdabang.zipdabang_android.ui.theme.NavBlack
import com.zipdabang.zipdabang_android.ui.theme.NoItem
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme


@Composable
fun NoRecentItem(){

    Box(
        modifier = Modifier
            .background(NoItem, shape = ZipdabangandroidTheme.Shapes.large)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "앗! 최근 봤던 아이템이 아직 없어요",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(700),
                color = NavBlack,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start=20.dp)
            )
            Text(
                "전체 상품 둘러보기",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(500),
                color = NavBlack,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start=20.dp)

            )
        }
    }

}

@Preview
@Composable
fun noItem(){
    NoRecentItem()
}