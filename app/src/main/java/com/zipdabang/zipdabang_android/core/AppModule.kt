package com.zipdabang.zipdabang_android.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat.getSystemService
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Constants.PAGING3_DATABASE
import com.zipdabang.zipdabang_android.common.Constants.BASE_URL
import com.zipdabang.zipdabang_android.common.Constants.RECIPE_DATABASE
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoRepository
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoRepositoryImpl
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoSerializer
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.storage.recipe.RecipeDatabase
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
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
    @Singleton
    fun provideRecipeDatabase(
        @ApplicationContext context: Context
    ): RecipeDatabase {

        return Room
            .databaseBuilder(
                context,
                RecipeDatabase::class.java,
                RECIPE_DATABASE
            )
            .build()
    }

    @Provides
    @Singleton // have a singleton...
    fun provideHttpClient(): OkHttpClient {

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

        return client
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
    @DeviceSize
    fun provideDeviceSize(
        @ApplicationContext appContext: Context
    ): DeviceScreenSize {
        fun px2dp(px: Int, context: Context): Float {
            return px / ((context.resources.displayMetrics.densityDpi.toFloat()) / DisplayMetrics.DENSITY_DEFAULT)
        }

        val display = appContext.resources.displayMetrics
        val deviceWidth = px2dp(display.widthPixels, appContext)
        val deviceHeight = px2dp(display.heightPixels, appContext)

        return DeviceScreenSize(deviceWidth, deviceHeight)
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

    @Provides
    @Singleton
    @NetworkConnection
    fun checkNetworkState(
        @ApplicationContext context: Context
    ): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(ConnectivityManager::class.java)
        val network: Network = connectivityManager.activeNetwork ?: return false
        val actNetwork: NetworkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
        }
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DeviceSize

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DeviceHeight

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkConnection
