package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun MyProfileScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .verticalScroll(scrollState),
    ){
        Column(
            modifier = Modifier
                .padding(16.dp, 20.dp, 16.dp, 20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(
                text = "한 줄 소개",
                style = ZipdabangandroidTheme.Typography.sixteen_700,
                color = ZipdabangandroidTheme.Colors.Typo,
            )
            Text(
                text = "산토끼가 강한 이유는 깡과 총이 있기",
                style = ZipdabangandroidTheme.Typography.sixteen_500,
                color = ZipdabangandroidTheme.Colors.Typo,
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "선호하는 카페 음료",
                style = ZipdabangandroidTheme.Typography.sixteen_700,
                color = ZipdabangandroidTheme.Colors.Typo,
            )
            LazyRow(

            ){

            }
        }

    }
}