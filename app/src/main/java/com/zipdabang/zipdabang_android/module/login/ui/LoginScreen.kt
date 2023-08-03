package com.zipdabang.zipdabang_android.module.login

import android.util.Log
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.splash.ui.SplashTitle
import com.zipdabang.zipdabang_android.ui.component.LoginButton
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.flow.onEach

@Composable
fun LoginScreen() {

    val TAG = "LoginScreen"

    val context = LocalContext.current
    val viewModel = viewModel<LoginViewModel>()

    val state = viewModel.state

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 180.dp,
                    bottom = 140.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            val googleIcon = R.drawable.login_google
            val kakaoIcon = R.drawable.login_kakao

            SplashTitle()

            Column(
                modifier = Modifier
                    .padding(top = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginButton(
                    icon = googleIcon ,
                    text = "구글로 시작하기",
                    onClick = {
                        /* TODO 구글 로그인 처리 */
                    },
                    backgroundColor = ZipdabangandroidTheme.Colors.SubBackground
                )

                Spacer(modifier = Modifier
                    .height(8.dp))

                LoginButton(
                    icon = kakaoIcon,
                    text = "카카오로 시작하기",
                    onClick = {
                        Log.d(TAG, "클릭")
                        viewModel.kakaoLogin(context)
                    },
                    backgroundColor = Color(0xFFFEE500)
                )
            }

            Button(
                modifier = Modifier,
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = ZipdabangandroidTheme.Colors.Typo
                ),
                shape = ZipdabangandroidTheme.Shapes.thin
            ) {
                Text(
                    modifier = Modifier,
                    text = "나중에 로그인하기",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium))
                )
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}*/
