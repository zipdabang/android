package com.zipdabang.zipdabang_android.module.recipes.di

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.data.banner.RecipeBannerRepositoryImpl
import com.zipdabang.zipdabang_android.module.recipes.data.category.RecipeCategoryRepositoryImpl
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceToggleRepositoryImpl
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeListRepositoryImpl
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggleRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeBannerRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeCategoryRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.CategoryRecipeListMediator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.assisted.AssistedFactory
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipeModule {
    @Provides
    fun provideRecipeApi(retrofit: Retrofit): RecipeApi {
        return retrofit.create(RecipeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeCategoryRepository(recipeApi: RecipeApi): RecipeCategoryRepository {
        return RecipeCategoryRepositoryImpl(recipeApi)
    }

    @Provides
    fun provideRecipeListRepository(
        recipeApi: RecipeApi,
        database: Paging3Database,
        dataStore: DataStore<Token>
    ): RecipeListRepository {
        return RecipeListRepositoryImpl(recipeApi, database, dataStore)
    }

    @Provides
    @Singleton
    fun providePreferenceToggleRepository(
        recipeApi: RecipeApi,
    ): PreferenceToggleRepository {
        return PreferenceToggleRepositoryImpl(recipeApi)
    }

    @Provides
    @Singleton
    fun provideRecipeBannerRepository(
        recipeApi: RecipeApi
    ): RecipeBannerRepository {
        return RecipeBannerRepositoryImpl(recipeApi)
    }

}
