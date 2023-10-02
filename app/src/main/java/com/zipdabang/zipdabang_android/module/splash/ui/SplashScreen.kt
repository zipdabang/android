package com.zipdabang.zipdabang_android.module.splash.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.navigation.AuthScreen
import com.zipdabang.zipdabang_android.core.navigation.SPLASH_ROUTE
import com.zipdabang.zipdabang_android.module.main.FCMData
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTokenValid: () -> Unit,
    onTokenInvalid: () -> Unit,
    fcmData: FCMData?,
    onNotificationClick: () -> Unit
) {
    val viewModel = hiltViewModel<SplashViewModel>()
    val thumbnail = R.drawable.splash_webp

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
                .padding(top = 100.dp, start = 20.dp)
        ) {
            SplashTitle()
            LaunchedEffect(key1 = true) {
                viewModel.checkAccessToken(
                    onTokenValid = onTokenValid,
                    onTokenInvalid = onTokenInvalid,
                    onNotificationClick = onNotificationClick,
                    fcmData = fcmData
                )
            }
        }
    }
}

/*
@Preview
@Composable()
fun SplashScreenPreview() {
    SplashScreen(rememberNavController())
}*/
