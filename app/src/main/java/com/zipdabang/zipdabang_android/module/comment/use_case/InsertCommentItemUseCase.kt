package com.zipdabang.zipdabang_android.module.comment.use_case

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.comment.domain.CommentMgtResult
import com.zipdabang.zipdabang_android.module.comment.domain.RecipeCommentRepository
import com.zipdabang.zipdabang_android.module.comment.ui.PostCommentState
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class InsertCommentItemUseCase @Inject constructor(
    private val repository: RecipeCommentRepository
) {
    operator fun invoke(commentItem: RecipeCommentState) = flow<Resource<CommentMgtResult>> {
        try {

        } catch (e: Exception) {
            if (e != CancellationException()) {
                emit(Resource.Error(e.message ?: "unexpected exception"))
            }
        }
    }
}