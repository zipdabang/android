package com.zipdabang.zipdabang_android.module.comment.util

import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentEntity
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentDto
import com.zipdabang.zipdabang_android.module.comment.data.remote.RecipeComment
import com.zipdabang.zipdabang_android.module.comment.domain.DeleteResult
import com.zipdabang.zipdabang_android.module.comment.domain.EditResult
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
        commentId = commentId,
        ownerId = ownerId,
        updatedAt = updatedAt
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
        ownerId = ownerId,
        updatedAt = updatedAt
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
        ownerId = ownerId,
        updatedAt = updatedAt
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

fun PostCommentDto.toEditResult(): EditResult {
    return EditResult(
        code = code,
        message = message,
        isConnectionSuccessful = isSuccess,
        isEditSuccessful = result != null
    )
}

fun ResponseBody<String?>.toDeleteResult(): DeleteResult {
    return DeleteResult(
        code = code,
        message = message,
        isConnectionSuccessful = isSuccess,
        isDeleteSuccessful = result != null
    )
}