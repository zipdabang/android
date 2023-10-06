package com.zipdabang.zipdabang_android.module.comment.util

import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.entity.recipe.RecipeCommentsEntity
import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentEntity
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentDto
import com.zipdabang.zipdabang_android.module.comment.data.remote.RecipeComment
import com.zipdabang.zipdabang_android.module.comment.data.remote.UserBlockDto
import com.zipdabang.zipdabang_android.module.comment.domain.DeleteResult
import com.zipdabang.zipdabang_android.module.comment.domain.EditResult
import com.zipdabang.zipdabang_android.module.comment.domain.PostResult
import com.zipdabang.zipdabang_android.module.comment.domain.ReportResult
import com.zipdabang.zipdabang_android.module.comment.domain.UserBlockResult
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentState

fun RecipeComment.recipeCommentsEntity(): RecipeCommentsEntity {
    return RecipeCommentsEntity(
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

fun RecipeCommentsEntity.toRecipeCommentState(): RecipeCommentState {
    return RecipeCommentState(
        itemId = itemId,
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

fun RecipeCommentState.toRecipeCommentsEntity(): RecipeCommentsEntity {
    return RecipeCommentsEntity(
        itemId = itemId,
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

fun ResponseBody<UserBlockDto?>.toUserBlockResult(): UserBlockResult {
    return UserBlockResult(
        code = code,
        message = message,
        isConnectionSuccessful = isSuccess,
        isBlockSuccessful = result != null
    )
}

fun ResponseBody<String?>.toReportResult(): ReportResult {
    return ReportResult(
        code = code,
        message = message,
        isConnectionSuccessful = isSuccess,
        isReportSuccessful = result != null
    )
}