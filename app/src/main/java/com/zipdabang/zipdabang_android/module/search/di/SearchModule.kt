package com.zipdabang.zipdabang_android.module.search.di

import com.zipdabang.zipdabang_android.BuildConfig
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.module.market.data.MarketApi
import com.zipdabang.zipdabang_android.module.market.data.MarketRepositoryImpl
import com.zipdabang.zipdabang_android.module.market.domain.MarketRepository
import com.zipdabang.zipdabang_android.module.search.data.SearchApi
import com.zipdabang.zipdabang_android.module.search.data.SearchRepositoryImpl
import com.zipdabang.zipdabang_android.module.search.domain.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent :: class)
object SearchModule {
    @Provides
    @Singleton
    fun provideSearchApi() : SearchApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(api : SearchApi) : SearchRepository {
        return SearchRepositoryImpl(api)
    }

}