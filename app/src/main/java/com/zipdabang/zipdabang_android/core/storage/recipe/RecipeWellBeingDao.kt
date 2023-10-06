package com.zipdabang.zipdabang_android.core.storage.recipe

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zipdabang.zipdabang_android.entity.recipe.RecipeAdeEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeCoffeeEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeWellBeingEntity
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem

@Dao
interface RecipeWellBeingDao {
    @Query("SELECT * FROM recipe_item_wellbeing_table")
    fun getAllRecipes(): PagingSource<Int, RecipeWellBeingEntity>

    @Query("SELECT * FROM recipe_item_wellbeing_table WHERE recipeId=:recipeId")
    suspend fun getRecipeItemById(recipeId: Int): RecipeWellBeingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(recipes: List<RecipeWellBeingEntity>)

    @Query("DELETE FROM recipe_item_wellbeing_table")
    suspend fun deleteAllRecipes()

    @Update
    suspend fun updateRecipe(recipe: RecipeWellBeingEntity)

    @Query("SELECT itemId FROM recipe_item_wellbeing_table WHERE recipeId=:recipeId")
    suspend fun getItemIdByRecipeId(recipeId: Int): String
}