package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FollowDao {

    @Query("SELECT * FROM follow_table")
    fun getAllItem() : PagingSource<Int, Following>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items : List<Following>)

    @Query("DELETE FROM follow_table")
    suspend fun deleteItems()

}