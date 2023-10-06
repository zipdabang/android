package com.zipdabang.zipdabang_android.core.storage.recipe

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.entity.recipe.RemoteKeysEntity

@Dao
interface RecipeRemoteKeyDao{
    @Query("SELECT * FROM recipe_remote_key_table WHERE id =:id")
    suspend fun getRemoteKeys(id: String) : RemoteKeysEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<RemoteKeysEntity>)

    @Query("DELETE FROM recipe_remote_key_table")
    suspend fun deleteRemoteKeys()

    @Query("DELETE FROM recipe_remote_key_table WHERE id=:id")
    suspend fun deleteSingleRemoteKey(id: String)

}