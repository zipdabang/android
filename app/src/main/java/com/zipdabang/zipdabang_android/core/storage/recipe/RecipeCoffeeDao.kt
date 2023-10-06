package com.zipdabang.zipdabang_android.core.storage.recipe

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zipdabang.zipdabang_android.entity.recipe.RecipeCoffeeEntity
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem

@Dao
interface RecipeCoffeeDao {
    @Query("SELECT * FROM recipe_item_coffee_table")
    fun getAllRecipes(): PagingSource<Int, RecipeCoffeeEntity>

    @Query("SELECT * FROM recipe_item_coffee_table WHERE recipeId=:recipeId")
    suspend fun getRecipeItemById(recipeId: Int): RecipeCoffeeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(recipes: List<RecipeCoffeeEntity>)

    @Query("DELETE FROM recipe_item_coffee_table")
    suspend fun deleteAllRecipes()

    @Update
    suspend fun updateRecipe(recipe: RecipeCoffeeEntity)

    @Query("SELECT itemId FROM recipe_item_coffee_table WHERE recipeId=:recipeId")
    suspend fun getItemIdByRecipeId(recipeId: Int): String
}