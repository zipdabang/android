package com.zipdabang.zipdabang_android.module.market.domain.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.Category_Product


@Dao
interface MarketCategoryDao {

    @Query("SELECT * FROM paging3_table")
    fun getAllItem() : PagingSource<Int,Category_Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items : List<Category_Product>)

    @Query("DELETE FROM paging3_table")
    suspend fun deleteItems()



}