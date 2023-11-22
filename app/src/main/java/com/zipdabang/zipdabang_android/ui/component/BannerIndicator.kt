package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme


@Composable
fun BannerIndicator(
    currentPage : Int,
    totalPage : Int
) {
    Box(
        modifier = Modifier
            .background(Color(0x33262D31),shape = ZipdabangandroidTheme.Shapes.large)
            .border(1.dp,color = Color(0x1A262D31),shape = ZipdabangandroidTheme.Shapes.large)
            .fillMaxSize()
    ){
        Text(text = "${currentPage}/${totalPage}",
            color = Color.White,
            style = ZipdabangandroidTheme.Typography.sixteen_500,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}