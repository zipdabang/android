package com.zipdabang.zipdabang_android.module.login.ui

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.module.login.platform_client.GoogleAuthClient
import com.zipdabang.zipdabang_android.module.login.platform_client.KakaoAuthClient
import com.zipdabang.zipdabang_android.module.login.data.member.AuthBody
import com.zipdabang.zipdabang_android.module.splash.ui.SplashTitle
import com.zipdabang.zipdabang_android.ui.component.LoginButton
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    tokenStoreViewModel: ProtoDataViewModel = hiltViewModel(),
    onSuccess: (String, String) -> Unit,
    onRegister: (String, String) -> Unit,
    onLoginLater: ()-> Unit
) {
    val TAG = "LoginScreen"

    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    val googleAuthClient by lazy {
        GoogleAuthClient(
            context = context.applicationContext,
            oneTapClient = Identity.getSignInClient(context.applicationContext)
        )
    }

    val kakaoAuthClient by lazy {
        KakaoAuthClient()
    }

    val isLoadingForLogin = viewModel.state.value.isLoading
    val isLoadingForTempLogin = viewModel.tempLoginState.value.isLoading

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            Log.d(TAG, "onResult ${result.resultCode}")
            if (result.resultCode == RESULT_OK) {
                scope.launch {
                    val signInResult = googleAuthClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    Log.d(TAG, "Sign In Result : $signInResult")

                    signInResult?.data?.let {
                        if (it.email != null && it.profile != null) {
                            val googleUserInfo =
                                UserLoginInfo(
                                    profile = it.profile,
                                    email = it.email
                                )

                            val profile = googleUserInfo.profile
                            val email = googleUserInfo.email
                            email?.let {
                                viewModel.getAuthResult(
                                    platform = Constants.PLATFORM_GOOGLE,
                                    email = email,
                                    profile = profile!!,
                                    tokenStoreViewModel = tokenStoreViewModel,
                                    onSuccess = onSuccess,
                                    onRegister = onRegister
                                )
                            }
                        }
                    }
                }
            }
        }
    )

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White //예은 - 배경 화이트로 추가
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

            SplashTitle(color = ZipdabangandroidTheme.Colors.Choco)

            Column(
                modifier = Modifier
                    .padding(top = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginButton(
                    icon = googleIcon ,
                    text = "구글로 시작하기",
                    onClick = {
                        scope.launch {
                            val signInIntentSender = googleAuthClient.signIn()
                            Log.d(TAG, "Google Sign In entering")
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )

                            // back-end database access

                            // main ui

                        }
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
                        scope.launch {
                            val result = kakaoAuthClient.signIn(context)
                            if (result.data?.profile != null && result.data.email != null) {
                                // back-end database access
                                val email = result.data.email
                                val profile = result.data.profile
                                Log.d(TAG, "email: $email, profile: $profile")
                                viewModel.getAuthResult(
                                    platform = Constants.PLATFORM_KAKAO,
                                    tokenStoreViewModel = tokenStoreViewModel,
                                    email = email,
                                    profile = profile,
                                    onSuccess = onSuccess,
                                    onRegister = onRegister
                                )
                            } else {

                            }
                        }
                        /*viewModel.apply {
                            resetState()
                            kakaoLogin(context)
                        }*/
                    },
                    backgroundColor = Color(0xFFFEE500)
                )
            }

            Button(
                modifier = Modifier,
                onClick = {
                    viewModel.getTempLoginResult(onLoginLater)
                },
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

        if (isLoadingForLogin || isLoadingForTempLogin) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = ZipdabangandroidTheme.Colors.Choco
                )
            }
        }
    }
}

/*
fun onAuthCompleted(state: AuthState, email: String, onSuccess: (String) -> Unit) {
    Log.d("LoginScreen", "$state, $email")
    if (!state.isLoading && state.token != null && state.error == null) {
        onSuccess(email)
    }
}*/
