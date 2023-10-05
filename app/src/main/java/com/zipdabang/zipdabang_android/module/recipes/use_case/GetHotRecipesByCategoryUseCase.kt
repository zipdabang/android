package com.zipdabang.zipdabang_android.module.recipes.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.mapper.toHotRecipes
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import java.util.concurrent.Flow
import javax.inject.Inject

class GetHotRecipesByCategoryUseCase @Inject constructor(
    private val tokens: DataStore<Token>,
    private val repository: RecipeListRepository
) {

    companion object {
        const val TAG = "GetHotRecipesByCategoryUseCase"
    }

    operator fun invoke(categoryId: Int) = flow<Resource<ResponseBody<List<HotRecipeItem>>>> {
        try {
            emit(Resource.Loading())
            val accessToken = "Bearer ${tokens.data.first().accessToken}"
            val result = repository
                .getHotRecipeListByCategory(accessToken, categoryId)
                .toHotRecipes()

            when (result.code) {
                ResponseCode.RESPONSE_DEFAULT.code -> {
                    emit(Resource.Success(
                        code = result.code,
                        message = result.message,
                        data = result
                    ))
                }
                ResponseCode.RESPONSE_NO_DATA.code -> {
                    emit(Resource.Success(
                        code = result.code,
                        message = result.message,
                        data = result
                    ))
                }
                else -> {
                    emit(Resource.Error(
                        message = result.message
                    ))
                }
            }

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            Log.e(TAG, errorBody?.string() ?: "error body is null")
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(Resource.Error(message = ResponseCode.getMessageByCode(errorCode)))
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.message ?: "unexpected io error"))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(message = e.message ?: "unexpected error"))
        }
    }
}