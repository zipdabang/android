package com.zipdabang.zipdabang_android.core.storage.recipe

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zipdabang.zipdabang_android.entity.recipe.RecipeAdeEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeAllEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeBaristaEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeCoffeeEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeUserEntity
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem

@Dao
interface RecipeUserDao {
    @Query("SELECT * FROM recipe_item_user_table")
    fun getAllRecipes(): PagingSource<Int, RecipeUserEntity>

    @Query("SELECT * FROM recipe_item_user_table WHERE recipeId=:recipeId")
    suspend fun getRecipeItemById(recipeId: Int): RecipeUserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(recipes: List<RecipeUserEntity>)

    @Query("DELETE FROM recipe_item_user_table")
    suspend fun deleteAllRecipes()

    @Update
    suspend fun updateRecipe(recipe: RecipeUserEntity)

    @Query("SELECT itemId FROM recipe_item_user_table WHERE recipeId=:recipeId")
    suspend fun getItemIdByRecipeId(recipeId: Int): String
}