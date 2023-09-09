package com.zipdabang.zipdabang_android.core

import android.content.Context
import android.util.DisplayMetrics
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Constants.PAGING3_DATABASE
import com.zipdabang.zipdabang_android.common.Constants.BASE_URL
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoRepository
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoRepositoryImpl
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoSerializer
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
/*        val MIGRATION_1_2 = object: Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //만약, 테이블이 추가 되었다면 어떤 테이블이 추가 되었는지 알려주는 query문장이 필요
                database.execSQL("CREATE TABLE 'recipe_item_table' ('categoryId' INTEGER, 'comments' INTEGER, 'createdAt' TEXT, 'isLiked' INTEGER, 'isScrapped' INTEGER, 'likes' INTEGER, 'nickname' TEXT, 'recipeName' TEXT, 'scraps' INTEGER, 'thumbnailUrl' TEXT " + "PRIMARY KEY('recipeId'))")
            }
        }*/

        return Room
            .databaseBuilder(
                context,
                Paging3Database::class.java,
                PAGING3_DATABASE
            )
            .fallbackToDestructiveMigration()
//            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton // have a singleton...
    fun provideHttpClient(
        tokenDataStore: DataStore<Token>
    ): OkHttpClient {

        // val accessToken = tokenDataStore.data.first().accessToken

        return OkHttpClient.Builder()
/*            .addInterceptor(Interceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", )
                    .build()
                chain.proceed(newRequest);
            })*/
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
            coerceInputValues = true
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            // which library to use for "de"serialization(JSON -> Object)
            // kotlinx-serialization dependency
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    @DeviceHeight
    fun provideDeviceHeight(
        @ApplicationContext appContext: Context
    ): Float {
        fun px2dp(px: Int, context: Context): Float {
            return px / ((context.resources.displayMetrics.densityDpi.toFloat()) / DisplayMetrics.DENSITY_DEFAULT)
        }

        val display = appContext.resources.displayMetrics
        val deviceWidth = display?.widthPixels
        val deviceHeight = display.heightPixels

        return px2dp(deviceHeight, appContext)
    }

/*    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    @AccessToken
    fun provideAccessToken(
        dataStore: DataStore<Token>
    ): String {
        lateinit var result: String

        val accessToken = CoroutineScope(Dispatchers.IO).async {
            "Bearer ${dataStore.data.first().accessToken}"
        }

        accessToken.invokeOnCompletion {
            if (it == null) {
                result = accessToken.getCompleted()
            }
        }

        return result
    }*/
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DeviceHeight