package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchFollowDao {
    @Query("SELECT * FROM search_follow_table ORDER BY `index`")
     fun getAllItem() : PagingSource<Int, FollowInfoDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items: MutableList<FollowInfoDB>)

    @Query("DELETE FROM search_follow_table")
    suspend fun deleteItems()
}