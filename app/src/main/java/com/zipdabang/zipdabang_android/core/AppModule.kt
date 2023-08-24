package com.zipdabang.zipdabang_android.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Constants.PAGING3_DATABASE
import com.zipdabang.zipdabang_android.common.Constants.BASE_URL
import com.zipdabang.zipdabang_android.core.data_store.ProtoRepository
import com.zipdabang.zipdabang_android.core.data_store.ProtoRepositoryImpl
import com.zipdabang.zipdabang_android.core.data_store.ProtoSerializer
import com.zipdabang.zipdabang_android.core.data_store.Token
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideProtoDataStore(
        @ApplicationContext applicationContext: Context
    ): DataStore<Token> {
        return DataStoreFactory.create(
            serializer = ProtoSerializer,
            produceFile = { applicationContext.dataStoreFile(Constants.DATA_STORE_FILE_NAME) },
            corruptionHandler = null,
        )
    }

    @Singleton
    @Provides
    fun provideTokenProtoRepository(
        @ApplicationContext applicationContext: Context,
        protoDataStore: DataStore<Token>
    ): ProtoRepository {
        return ProtoRepositoryImpl(applicationContext, protoDataStore)
    }

    @Provides

    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): Paging3Database {
        return Room.databaseBuilder(
            context,
            Paging3Database::class.java,
            PAGING3_DATABASE
        ).build()

        @Singleton // have a singleton...
        fun provideHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
        }

        @OptIn(ExperimentalSerializationApi::class)
        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            val contentType = "application/json".toMediaType()
            val json = Json {
                // specifies whether encounters of unknown properties in the input JSON should be ignored,
                // instead of exception(SerializationException)
                ignoreUnknownKeys = true
            }
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                // which library to use for "de"serialization(JSON -> Object)
                // kotlinx-serialization dependency
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
        }
    }
}
