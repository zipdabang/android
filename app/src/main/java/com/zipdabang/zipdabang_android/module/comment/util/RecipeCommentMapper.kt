package com.zipdabang.zipdabang_android.module.comment.util

import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentEntity
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentDto
import com.zipdabang.zipdabang_android.module.comment.data.remote.RecipeComment
import com.zipdabang.zipdabang_android.module.comment.domain.PostResult
import java.util.Random

fun RecipeComment.toRecipeCommentEntity(): RecipeCommentEntity {
    return RecipeCommentEntity(
        content = content,
        createdAt = createdAt,
        isOwner = isOwner,
        ownerImage = ownerImage,
        ownerNickname = ownerNickname,
        // TODO commentId와 oweerId 요구하여 고치기
        commentId = Random().nextInt(),
        ownerId = Random().nextInt()
    )
}

fun PostCommentDto.toPostResult(): PostResult {
    return PostResult(
        code = code,
        message = message,
        isConnectionSuccessful = isSuccess,
        isPostSuccessful = result != null
    )
}