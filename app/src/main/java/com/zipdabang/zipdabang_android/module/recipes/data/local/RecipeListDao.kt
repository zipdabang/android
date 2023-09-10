package com.zipdabang.zipdabang_android.module.recipes.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem

@Dao
interface RecipeListDao {
    @Query("SELECT * FROM recipe_item_table")
    fun getAllRecipes(): PagingSource<Int, RecipeItemEntity>

    @Query("SELECT * FROM recipe_item_table WHERE recipeId=:recipeId")
    suspend fun getRecipeItemById(recipeId: Int): RecipeItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(recipes: List<RecipeItemEntity>)

    @Query("DELETE FROM recipe_item_table")
    suspend fun deleteAllRecipes()

    @Update
    suspend fun updateRecipe(recipe: RecipeItemEntity)

    @Query("SELECT itemId FROM recipe_item_table WHERE recipeId=:recipeId")
    suspend fun getItemIdByRecipeId(recipeId: Int): Int
}