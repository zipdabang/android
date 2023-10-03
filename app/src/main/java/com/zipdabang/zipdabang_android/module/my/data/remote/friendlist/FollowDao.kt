package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.Category_Product

@Dao
interface FollowDao {

    @Query("SELECT * FROM follow_table")
    fun getAllItem() : PagingSource<Int,FollowDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items : List<FollowDto>)

    @Query("DELETE FROM paging3_table")
    suspend fun deleteItems()

}