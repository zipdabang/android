package com.zipdabang.zipdabang_android.module.recipes.di

import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.data.category.RecipeCategoryRepositoryImpl
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceToggleRepositoryImpl
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipeListRepositoryImpl
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggleRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeCategoryRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
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
    @Singleton
    fun provideRecipeCategoryRepository(recipeApi: RecipeApi): RecipeCategoryRepository {
        return RecipeCategoryRepositoryImpl(recipeApi)
    }

    @Provides
    fun provideRecipeListRepository(recipeApi: RecipeApi): RecipeListRepository {
        return RecipeListRepositoryImpl(recipeApi)
    }

    @Provides
    @Singleton
    fun providePreferenceToggleRepository(
        recipeApi: RecipeApi,
    ): PreferenceToggleRepository {
        return PreferenceToggleRepositoryImpl(recipeApi)
    }

}