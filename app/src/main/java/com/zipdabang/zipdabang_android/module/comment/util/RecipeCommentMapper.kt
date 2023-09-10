package com.zipdabang.zipdabang_android.module.comment.util

import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentEntity
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentDto
import com.zipdabang.zipdabang_android.module.comment.data.remote.RecipeComment
import com.zipdabang.zipdabang_android.module.comment.domain.PostResult
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentState
import java.util.Random
import java.util.concurrent.ThreadLocalRandom

fun RecipeComment.toRecipeCommentEntity(): RecipeCommentEntity {
    return RecipeCommentEntity(
        content = content,
        createdAt = createdAt,
        isOwner = isOwner,
        ownerImage = ownerImage,
        ownerNickname = ownerNickname,
        // TODO commentId와 ownerId 요구하여 고치기
        commentId = ThreadLocalRandom.current().nextInt(1, 100000),
        ownerId = ThreadLocalRandom.current().nextInt(1, 100000)
    )
}

fun RecipeCommentEntity.toRecipeCommentState(): RecipeCommentState {
    return RecipeCommentState(
        content = content,
        createdAt = createdAt,
        isOwner = isOwner,
        ownerImage = ownerImage,
        ownerNickname = ownerNickname,
        commentId = commentId,
        ownerId = ownerId
    )
}

fun RecipeCommentState.toRecipeCommentEntity(): RecipeCommentEntity {
    return RecipeCommentEntity(
        itemId = 0,
        content = content,
        createdAt = createdAt,
        isOwner = isOwner,
        ownerImage = ownerImage,
        ownerNickname = ownerNickname,
        commentId = commentId,
        ownerId = ownerId
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