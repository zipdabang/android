package com.zipdabang.zipdabang_android.module.login.platform_client

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.zipdabang.zipdabang_android.module.login.ui.SocialLoginResult
import com.zipdabang.zipdabang_android.module.login.ui.UserLoginInfo
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class KakaoAuthClient() {
    // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인

    companion object {
        const val TAG = "KakaoAuthClient"
    }


    suspend fun signIn(context: Context): SocialLoginResult {
        val accessToken = kakaoAuthentication(context)
        return if (accessToken != null) {
            val userInfo = getKakaoUserInfo()
            if (userInfo.profile != null && userInfo.email != null) {
                SocialLoginResult(
                    data = userInfo,
                )

            } else {
                SocialLoginResult(
                    data = null,
                    error = "kakao user info derivation failure: ${getKakaoUserInfo()}"
                )
            }

        } else {
            SocialLoginResult(
                data = null,
                error = "kakao sign in failure"
            )
        }
    }

    private suspend fun kakaoAuthentication(context: Context) = suspendCoroutine { continuation ->

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
                continuation.resume(token)

            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                continuation.resume(null)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        continuation.resume(null)
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    continuation.resume(token)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }


    private suspend fun getKakaoUserInfo(): UserLoginInfo = suspendCoroutine { continuation ->
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
                continuation.resume(UserLoginInfo(null, null))
            }
            else if (user != null) {
                Log.i(
                    TAG, "사용자 정보 요청 성공" +
                        "\n닉네임: ${user.kakaoAccount?.email}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                val email = user.kakaoAccount?.email
                val profile = user.kakaoAccount?.profile?.thumbnailImageUrl
                continuation.resume(UserLoginInfo(email, profile))
            }
        }
    }
}