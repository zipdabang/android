package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme


@Composable
fun FollowScreen() {
    LazyColumn(modifier = Modifier.padding(30.dp)
    ) {
        items(200) {
            Text(
                "팔로우",
                color = ZipdabangandroidTheme.Colors.Typo,
                style = ZipdabangandroidTheme.Typography.sixteen_500
            )
        }}
}