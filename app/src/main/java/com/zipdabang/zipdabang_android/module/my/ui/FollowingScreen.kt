package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun FollowingScreen(){
    val scrollState = rememberScrollState()
    LazyColumn(modifier = Modifier.padding(30.dp)
        ) {
        items(200) {
            Text(
                "팔로잉",
                color = ZipdabangandroidTheme.Colors.Typo,
                style = ZipdabangandroidTheme.Typography.sixteen_500
            )
        }}
    }