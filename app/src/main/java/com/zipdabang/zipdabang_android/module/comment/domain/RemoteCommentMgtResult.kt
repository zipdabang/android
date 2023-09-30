package com.zipdabang.zipdabang_android.module.comment.domain

data class PostResult(
    val code: Int,
    val message: String,
    val isConnectionSuccessful: Boolean,
    val isPostSuccessful: Boolean
)
data class DeleteResult(
    val code: Int,
    val message: String,
    val isConnectionSuccessful: Boolean,
    val isDeleteSuccessful: Boolean
)

data class EditResult(
    val code: Int,
    val message: String,
    val isConnectionSuccessful: Boolean,
    val isEditSuccessful: Boolean
)

data class UserBlockResult(
    val code: Int,
    val message: String,
    val isConnectionSuccessful: Boolean,
    val isBlockSuccessful: Boolean
)
