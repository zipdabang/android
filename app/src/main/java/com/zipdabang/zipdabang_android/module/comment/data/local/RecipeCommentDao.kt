package com.zipdabang.zipdabang_android.module.comment.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecipeCommentDao {
    @Query("SELECT * FROM comment_item_table")
    fun getAllComments(): PagingSource<Int, RecipeCommentEntity>

    @Query("SELECT * FROM comment_item_table WHERE commentId=:commentId")
    suspend fun getCommentItemById(commentId: Int): RecipeCommentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComments(comments: List<RecipeCommentEntity>)

    @Query("DELETE FROM comment_item_table")
    suspend fun deleteAllComments()

    @Update
    suspend fun updateComment(comment: RecipeCommentEntity)

    @Query("SELECT itemId FROM comment_item_table WHERE commentId=:commentId")
    suspend fun getItemIdByCommentId(commentId: Int): Int
}