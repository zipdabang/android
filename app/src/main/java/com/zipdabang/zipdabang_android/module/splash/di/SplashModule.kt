package com.zipdabang.zipdabang_android.module.splash.di

import com.zipdabang.zipdabang_android.module.splash.data.AutoLoginRepositoryImpl
import com.zipdabang.zipdabang_android.module.splash.domain.AutoLoginApi
import com.zipdabang.zipdabang_android.module.splash.domain.AutoLoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SplashModule {

    @Provides
    @Singleton
    fun provideAutoLoginApi(retrofit: Retrofit): AutoLoginApi {
        return retrofit.create(AutoLoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAutoLoginRepository(
        api: AutoLoginApi
    ): AutoLoginRepository {
        return AutoLoginRepositoryImpl(api)
    }
}