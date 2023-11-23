package com.zipdabang.zipdabang_android.module.drawer.di

import com.zipdabang.zipdabang_android.BuildConfig
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.module.drawer.data.remote.DrawerApi
import com.zipdabang.zipdabang_android.module.drawer.data.repository.DrawerRepositoryImpl
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DrawerModule {

    @Provides
    @Singleton
    fun provideDrawerApi() : DrawerApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build())
            .build()
            .create(DrawerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDrawerRepository(api : DrawerApi) : DrawerRepository {
        return DrawerRepositoryImpl(api)
    }
}