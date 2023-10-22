package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.temp

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TempRecipesDao {
    @Query("SELECT * FROM my_temped_recipes_table")
    fun getAllItem() : PagingSource<Int, TempRecipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items : List<TempRecipe>)

    @Query("DELETE FROM my_temped_recipes_table")
    suspend fun deleteItems()
}