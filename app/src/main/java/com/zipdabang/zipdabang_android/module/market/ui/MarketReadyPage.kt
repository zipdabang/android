package com.zipdabang.zipdabang_android.module.market.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.DialogBackground
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun MarketReadyPage() {
    Box(
        modifier = Modifier.wrapContentHeight()
            .fillMaxWidth()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(450.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = "현재 준비 중인 서비스에요!",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.scdream_bold)),
                    fontWeight = FontWeight(900),
                    color = Color(0xFF867768),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "곧 찾아뵙겠습니다. 기대해주세요:)",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                    fontWeight = FontWeight(300),
                    color = Color(0xFF262D31),
                    textAlign = TextAlign.Center,
                )
            )


            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier.size(
                    width = 216.dp, height = 182.dp
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.market_ready),
                    contentDescription = "market_ready", modifier = Modifier.fillMaxSize()
                )
            }


        }
    }
}