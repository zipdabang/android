package com.zipdabang.zipdabang_android.core.storage.recipe

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zipdabang.zipdabang_android.entity.recipe.RecipeCommentsEntity

@Dao
interface RecipeCommentsDao {
    @Query("SELECT * FROM recipe_comment_item_table")
    fun getAllComments(): PagingSource<Int, RecipeCommentsEntity>

    @Query("SELECT * FROM recipe_comment_item_table WHERE commentId=:commentId")
    suspend fun getCommentItemById(commentId: Int): RecipeCommentsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComments(comments: List<RecipeCommentsEntity>)

    @Query("DELETE FROM recipe_comment_item_table")
    suspend fun deleteAllComments()

    @Update
    suspend fun updateComment(comment: RecipeCommentsEntity)

    @Query("SELECT itemId FROM recipe_comment_item_table WHERE commentId=:commentId")
    suspend fun getItemIdByCommentId(commentId: Int): String

    @Query("DELETE FROM recipe_comment_item_table WHERE commentId=:commentId")
    suspend fun deleteComment(commentId: Int)
}