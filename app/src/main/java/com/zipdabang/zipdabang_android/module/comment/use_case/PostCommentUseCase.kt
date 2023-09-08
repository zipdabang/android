package com.zipdabang.zipdabang_android.module.comment.use_case

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentContent
import com.zipdabang.zipdabang_android.module.comment.domain.PostResult
import com.zipdabang.zipdabang_android.module.comment.domain.RecipeCommentRepository
import com.zipdabang.zipdabang_android.module.comment.util.toPostResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostCommentUseCase @Inject constructor(
    private val recipeCommentRepository: RecipeCommentRepository,
    private val tokenDataStore: DataStore<Token>
) {
    operator fun invoke(
        recipeId: Int, commentBody: PostCommentContent
    ) = flow<Resource<PostResult>> {
        try {
            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)

            emit(Resource.Loading())

            val response = recipeCommentRepository
                .postRecipeComment(accessToken, recipeId, commentBody)
                .toPostResult()

            // TODO 코드에 따른 분기처리 하기
            emit(Resource.Success(
                data = response,
                code = response.code,
                message = response.message ?: "unexpected error"
            ))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "unexpected io error"))

        }
    }
}