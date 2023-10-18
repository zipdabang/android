package com.zipdabang.zipdabang_android.module.main.di

import com.zipdabang.zipdabang_android.module.main.data.NotificationApi
import com.zipdabang.zipdabang_android.module.main.data.NotificationRepositoryImpl
import com.zipdabang.zipdabang_android.module.main.domain.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Provides
    @Singleton
    fun provideNotificationApi(retrofit: Retrofit): NotificationApi {
        return retrofit.create(NotificationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(
        notificationApi: NotificationApi
    ): NotificationRepository {
        return NotificationRepositoryImpl(notificationApi)
    }

}