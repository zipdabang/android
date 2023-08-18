package com.zipdabang.zipdabang_android.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.core.data_store.ProtoRepository
import com.zipdabang.zipdabang_android.core.data_store.ProtoRepositoryImpl
import com.zipdabang.zipdabang_android.core.data_store.ProtoSerializer
import com.zipdabang.zipdabang_android.core.data_store.Token
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
}
