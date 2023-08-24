package com.zipdabang.zipdabang_android.module.recipes.di

import com.zipdabang.zipdabang_android.core.data_store.ProtoDataViewModel
import com.zipdabang.zipdabang_android.core.data_store.ProtoRepository
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceToggleRepositoryImpl
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewRepositoryImpl
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggleRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipePreviewRepository
import dagger.Module
import dagger.Provides
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
    fun provideRecipePreviewRepository(recipeApi: RecipeApi): RecipePreviewRepository {
        return RecipePreviewRepositoryImpl(recipeApi)
    }

    @Provides
    @Singleton
    fun providePreferenceToggleRepository(
        recipeApi: RecipeApi,
    ): PreferenceToggleRepository {
        return PreferenceToggleRepositoryImpl(recipeApi)
    }


}