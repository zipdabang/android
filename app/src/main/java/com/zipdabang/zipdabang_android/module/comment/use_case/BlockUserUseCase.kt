package com.zipdabang.zipdabang_android.module.comment.use_case

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.comment.domain.RecipeCommentRepository
import com.zipdabang.zipdabang_android.module.comment.domain.UserBlockResult
import com.zipdabang.zipdabang_android.module.comment.util.toUserBlockResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class BlockUserUseCase @Inject constructor(
    private val recipeCommentRepository: RecipeCommentRepository,
    private val tokenDataStore: DataStore<Token>
) {
    operator fun invoke(ownerId: Int): Flow<Resource<UserBlockResult>> = flow {
        try {
            emit(Resource.Loading())
            tokenDataStore.data.first().accessToken?.let {
                val accessToken = "Bearer $it"
                val result = recipeCommentRepository.blockUser(accessToken, ownerId).toUserBlockResult()

                when (result.code) {
                    ResponseCode.RESPONSE_DEFAULT.code -> {
                        emit(Resource.Success(
                            data = result,
                            code = result.code,
                            message = result.message
                        ))
                    }
                    else -> {
                        emit(Resource.Error(message = result.message))
                    }
                }
            }

        } catch (e: HttpException) {
            emit(Resource.Error(message =  e.message ?: "unexpected http exception"))
        } catch (e: IOException) {
            emit(Resource.Error(message =  e.message ?: "unexpected io exception"))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(message =  e.message ?: "unexpected exception"))
        }
    }
}