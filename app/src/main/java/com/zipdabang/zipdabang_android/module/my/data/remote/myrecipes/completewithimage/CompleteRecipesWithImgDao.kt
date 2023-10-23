package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.completewithimage

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.like.LikeRecipe

@Dao
interface CompleteRecipesWithImgDao {
    @Query("SELECT * FROM my_complete_recipes_with_images_table")
    fun getAllItem() : PagingSource<Int, CompleteRecipeWithImg>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items : List<CompleteRecipeWithImg>)

    @Query("DELETE FROM my_complete_recipes_with_images_table")
    suspend fun deleteItems()
}