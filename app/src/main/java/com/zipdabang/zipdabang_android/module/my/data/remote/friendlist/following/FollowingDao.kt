package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FollowingDao {

    @Query("SELECT * FROM following_table")
    fun getAllItem() : PagingSource<Int, Follower>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items : List<Follower>)

    @Query("DELETE FROM following_table")
    suspend fun deleteItems()

}