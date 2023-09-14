package com.zipdabang.zipdabang_android.module.search.domain

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.Category_Product
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe

@Dao
interface SearchDao {

    @Query("SELECT * FROM search_recipe_table ORDER BY `index`")
    fun getAllItem() : PagingSource<Int, SearchRecipe>

    @Query("SELECT recipeId FROM search_recipe_table")
    fun getAllId() : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items : List<SearchRecipe>)

    @Query("DELETE FROM search_recipe_table")
    suspend fun deleteItems()



}