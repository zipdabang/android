package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.Following

@Dao
interface SearchFollowerDao {
    @Query("SELECT * FROM search_following_table")
    fun getAllItem() : PagingSource<Int, FollowerInfoDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items : List<FollowerInfoDB>)

    @Query("DELETE FROM search_following_table")
    suspend fun deleteItems()
}