package com.zipdabang.zipdabang_android.core

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.zipdabang.zipdabang_android.BuildConfig

class ZipdabangApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        var keyHash = Utility.getKeyHash(this)
        Log.i("keyHash string", keyHash)

    }
}