package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CompleteRecipesDao {
    @Query("SELECT * FROM my_complete_recipes_table")
    fun getAllItem() : PagingSource<Int, CompleteRecipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items : List<CompleteRecipe>)

    @Query("DELETE FROM my_complete_recipes_table")
    suspend fun deleteItems()
}