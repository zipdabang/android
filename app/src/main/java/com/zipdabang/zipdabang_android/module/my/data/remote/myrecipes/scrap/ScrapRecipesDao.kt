package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.scrap

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScrapRecipesDao {

    @Query("SELECT * FROM my_scraped_recipes_table")
    fun getAllItem() : PagingSource<Int, ScrapRecipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items : List<ScrapRecipe>)

    @Query("DELETE FROM my_scraped_recipes_table")
    suspend fun deleteItems()
}