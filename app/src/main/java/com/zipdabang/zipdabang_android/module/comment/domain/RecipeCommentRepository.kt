package com.zipdabang.zipdabang_android.module.comment.domain

import androidx.paging.Pager
import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentEntity
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentContent
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentDto
import com.zipdabang.zipdabang_android.module.comment.data.remote.UserBlockDto
import retrofit2.http.Path

interface RecipeCommentRepository {
    fun getRecipeComments(recipeId: Int): Pager<Int, RecipeCommentEntity>

    suspend fun postRecipeComment(
        accessToken: String, recipeId: Int, commentBody: String
    ): PostCommentDto

    suspend fun deleteRecipeComment(
        accessToken: String,
        recipeId: Int,
        commentId: Int
    ): ResponseBody<String?>

    suspend fun editRecipeComment(
        accessToken: String,
        recipeId: Int,
        commentId: Int,
        newContent: String
    ): PostCommentDto

    suspend fun blockUser(
        accessToken: String,
        ownerId: Int
    ): ResponseBody<UserBlockDto?>

    suspend fun reportComment(
        accessToken: String,
        recipeId: Int,
        commentId: Int,
        reportId: Int
    ): ResponseBody<String?>
}