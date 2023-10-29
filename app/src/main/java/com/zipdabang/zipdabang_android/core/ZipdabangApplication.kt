package com.zipdabang.zipdabang_android.core

import android.app.Application
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.zipdabang.zipdabang_android.BuildConfig
import com.zipdabang.zipdabang_android.common.ResponseCode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZipdabangApplication: Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        var keyHash = Utility.getKeyHash(this)
        Log.i("keyHash string", keyHash)

    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .build()
    }
}