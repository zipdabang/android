package com.zipdabang.zipdabang_android.module.my.domain.usecase

import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteRequest
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteResult
import com.zipdabang.zipdabang_android.module.my.data.remote.followorcancel.FollowOrCancelDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostFollowOrCancelUseCase @Inject constructor(
    private val repository : MyApi,
    private val dataStore : DataStore<Token>
) {
    operator fun invoke(targetId : Int) : Flow<Resource<FollowOrCancelDto>> = flow{
        try{
            emit(Resource.Loading())
            val token = "Bearer ${dataStore.data.first().accessToken}"
            val followOrCancelResponse = repository.postFollowOrCancel(accessToken = token, targetId = targetId)
            emit(
                Resource.Success(
                data = followOrCancelResponse,
                code = followOrCancelResponse.code,
                message = followOrCancelResponse.message
            )
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}