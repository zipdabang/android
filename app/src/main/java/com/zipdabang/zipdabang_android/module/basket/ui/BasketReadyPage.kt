package com.zipdabang.zipdabang_android.module.basket.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun BasketReadyPage() {
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
                style = ZipdabangandroidTheme.Typography.twentyfour_900_scdream,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "곧 찾아뵙겠습니다. 기대해주세요:)",
                style = ZipdabangandroidTheme.Typography.eighteen_300,
                color = ZipdabangandroidTheme.Colors.Typo
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier.size(width = 216.dp, height = 249.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.basket_ready),
                    contentDescription = "basket_ready",
                    modifier = Modifier.fillMaxSize()
                )
            }


        }
    }
}