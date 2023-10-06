package com.zipdabang.zipdabang_android.module.recipes.domain

import androidx.paging.Pager
import androidx.paging.PagingData
import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.entity.recipe.RecipeAdeEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeAllEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeBaristaEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeCoffeeEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeFruitEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeNonCaffeineEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeSmoothieEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeTeaEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeUserEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeWellBeingEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeZipdabangEntity
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeDto
import com.zipdabang.zipdabang_android.module.recipes.data.local.RecipeItemEntity
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewItemsDto
import com.zipdabang.zipdabang_android.module.recipes.data.recipe_list.RecipeListDto
import kotlinx.coroutines.flow.Flow

interface RecipeListRepository {
    // suspend fun getRecipePreviewList(accessToken: String, ownerType: String): RecipePreviewItemsDto

/*    fun getRecipeListByOwnerType(
        ownerType: String,
        orderBy: String,
    ): Pager<Int, RecipeItemEntity>*/

    fun getZipdabangRecipeList(
        orderBy: String,
    ): Pager<Int, RecipeZipdabangEntity>

    fun getBaristaRecipeList(
        orderBy: String,
    ): Pager<Int, RecipeBaristaEntity>

    fun getUserRecipeList(
        orderBy: String,
    ): Pager<Int, RecipeUserEntity>

/*    fun getRecipeListByCategory(
        categoryId: Int,
        orderBy: String
    ): Pager<Int, RecipeItemEntity>*/

    fun getRecipeCoffeeList(
        orderBy: String
    ): Pager<Int, RecipeCoffeeEntity>

    fun getRecipeNonCaffeineList(
        orderBy: String
    ): Pager<Int, RecipeNonCaffeineEntity>

    fun getRecipeTeaList(
        orderBy: String
    ): Pager<Int, RecipeTeaEntity>

    fun getRecipeAdeList(
        orderBy: String
    ): Pager<Int, RecipeAdeEntity>

    fun getRecipeSmoothieList(
        orderBy: String
    ): Pager<Int, RecipeSmoothieEntity>

    fun getRecipeFruitList(
        orderBy: String
    ): Pager<Int, RecipeFruitEntity>

    fun getRecipeWellBeingList(
        orderBy: String
    ): Pager<Int, RecipeWellBeingEntity>

    fun getRecipeAllList(
        orderBy: String
    ): Pager<Int, RecipeAllEntity>

    suspend fun getHotRecipeListByCategory(
        accessToken: String,
        categoryId: Int
    ): ResponseBody<HotRecipeDto>

    suspend fun toggleLikeRemote(
        accessToken: String,
        recipeId: Int
    ): PreferenceResultDto

    suspend fun toggleLikeLocalByUser(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto,
        ownerType: String
    ): PreferenceResultDto

    suspend fun toggleLikeLocalByCategory(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto,
        categoryId: Int
    ): PreferenceResultDto

    suspend fun toggleScrapRemote(
        accessToken: String,
        recipeId: Int
    ): PreferenceResultDto

    suspend fun toggleScrapLocalByUser(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto,
        ownerType: String
    ): PreferenceResultDto

    suspend fun toggleScrapLocalByCategory(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto,
        categoryId: Int
    ): PreferenceResultDto
}