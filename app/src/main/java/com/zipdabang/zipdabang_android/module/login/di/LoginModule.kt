package com.zipdabang.zipdabang_android.module.login.di

import com.zipdabang.zipdabang_android.BuildConfig
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.module.login.data.AuthApi
import com.zipdabang.zipdabang_android.module.login.data.AuthRepositoryImpl
import com.zipdabang.zipdabang_android.module.login.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {
    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {

        val interceptor = HttpLoggingInterceptor().apply {
            // level : BODY -> logs headers + bodies of request, response
            // NONE, BASIC, HEADERS, BODY
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            connectTimeout(30, TimeUnit.SECONDS)
            // readTimeout : maximum time gap between 'arrivals' of two data packets when waiting for the server's response
            readTimeout(20, TimeUnit.SECONDS)
            // writeTimeout : maximum time gap between two data packets when 'sending' them to the server
            writeTimeout(25, TimeUnit.SECONDS)
        }.build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
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