package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.like

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikeRecipesDao {
    @Query("SELECT * FROM my_liked_recipes_table")
    fun getAllItem() : PagingSource<Int, LikeRecipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items : List<LikeRecipe>)

    @Query("DELETE FROM my_liked_recipes_table")
    suspend fun deleteItems()
}