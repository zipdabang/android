package com.zipdabang.zipdabang_android.module.comment.domain

import androidx.paging.Pager
import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentEntity
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentContent
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentDto

interface RecipeCommentRepository {
    fun getRecipeComments(recipeId: Int): Pager<Int, RecipeCommentEntity>

    suspend fun postRecipeComment(accessToken: String, recipeId: Int, commentBody: PostCommentContent): PostCommentDto
}