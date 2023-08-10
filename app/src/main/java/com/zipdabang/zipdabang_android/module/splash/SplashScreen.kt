package com.zipdabang.zipdabang_android.module.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.splash.ui.SplashTitle

@Composable
fun SplashScreen() {

    val thumbnail = R.drawable.splash_thumbnail

    Box(modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(id = thumbnail),
            sizeToIntrinsics = true,
            contentScale = ContentScale.FillBounds
        )
    ) {
        Column(
            modifier = Modifier
                .padding(top = 120.dp, start = 20.dp)
        ) {
            SplashTitle()
        }
    }
}

@Preview
@Composable()
fun SplashScreenPreview() {
    SplashScreen()
}