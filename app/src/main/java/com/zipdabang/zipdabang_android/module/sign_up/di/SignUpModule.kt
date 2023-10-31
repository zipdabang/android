package com.zipdabang.zipdabang_android.module.sign_up.di

import com.zipdabang.zipdabang_android.BuildConfig
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.SignUpApi
import com.zipdabang.zipdabang_android.module.sign_up.data.repository.SignUpRepositoryImpl
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SignUpModule {

    @Provides
    @Singleton
    fun provideSignupApi() : SignUpApi{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SignUpApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSignUpRepositoy(api : SignUpApi) : SignUpRepository{
        return SignUpRepositoryImpl(api)
    }
}