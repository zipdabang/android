package com.zipdabang.zipdabang_android.module.home.di

import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.module.home.data.HomeApi
import com.zipdabang.zipdabang_android.module.home.data.HomeRepositoryImpl
import com.zipdabang.zipdabang_android.module.home.domain.HomeRepository
import com.zipdabang.zipdabang_android.module.market.data.MarketApi
import com.zipdabang.zipdabang_android.module.market.data.MarketRepositoryImpl
import com.zipdabang.zipdabang_android.module.market.domain.MarketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent ::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeApi() : HomeApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideMarketRepository(api : HomeApi) : HomeRepository {
        return HomeRepositoryImpl(api)
    }

}