package com.zipdabang.zipdabang_android.module.login.di

import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.module.login.data.AuthApi
import com.zipdabang.zipdabang_android.module.login.data.AuthRepositoryImpl
import com.zipdabang.zipdabang_android.module.login.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {
    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi): AuthRepository {
        return AuthRepositoryImpl(api)
    }
}