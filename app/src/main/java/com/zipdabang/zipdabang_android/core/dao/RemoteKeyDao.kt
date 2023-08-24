package com.zipdabang.zipdabang_android.core.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.RemoteKeys

@Dao
interface RemoteKeyDao{

    @Query("SELECT * FROM remote_key_table WHERE id =:id")
    suspend fun getRemoteKeys(id: Int) : RemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<RemoteKeys>)

    @Query("DELETE FROM remote_key_table")
    suspend fun deleteRemoteKeys()



}