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
    fun getAllRecipes(): PagingSource<Int, RecipeItem>

    @Query("SELECT * FROM recipe_item_table WHERE recipeId=:recipeId")
    suspend fun getRecipeItemById(recipeId: Int): RecipeItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(recipes: List<RecipeItem>)

    @Query("DELETE FROM recipe_item_table")
    suspend fun deleteAllRecipes()

    @Update
    suspend fun updateRecipe(recipe: RecipeItem)
}