package com.zipdabang.zipdabang_android.module.market.di

import com.google.firebase.crashlytics.buildtools.reloc.com.google.j2objc.annotations.Property
import com.zipdabang.zipdabang_android.common.Constants
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
@InstallIn(SingletonComponent :: class)
object MarketModule {


    //개발자가 생성자를 삽입할 수 없을 때 @Provides 어노테이션 사용
    @Provides
    @Singleton
    fun provideMarketApi() : MarketApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarketApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideMarketRepository(api : MarketApi) : MarketRepository {
        return MarketRepositoryImpl(api)
    }



}