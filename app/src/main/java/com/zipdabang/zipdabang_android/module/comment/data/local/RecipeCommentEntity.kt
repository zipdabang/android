package com.zipdabang.zipdabang_android.module.comment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants.COMMENT_ITEM_TABLE
import kotlinx.serialization.Serializable

@Entity(tableName = COMMENT_ITEM_TABLE)
data class RecipeCommentEntity(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val content: String,
    val createdAt: String,
    val isOwner: Boolean,
    val ownerImage: String,
    val ownerNickname: String,
    val commentId: Int,
    val ownerId: Int
)
/*
TODO 질문사항
1. 댓글 API에 자기꺼 프로필 사진도 보내주어야함 -> 재탕?
2. 신고는 어떤거로 보내줘야 하는지?(commentId로 신고하는 것 아닌지?)
3. 차단은 어떤거로 보내줘야 하는기?(ownerId로 차단하는 것 아닌지?)
 */