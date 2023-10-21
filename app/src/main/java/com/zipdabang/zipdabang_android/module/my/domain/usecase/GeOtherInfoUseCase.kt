package com.zipdabang.zipdabang_android.module.my.domain.usecase

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherInfoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GeOtherInfoUseCase @Inject constructor(
    private val repository : MyApi,
    private val dataStore : DataStore<Token>
) {
    operator fun invoke(targetId : Int) : Flow<Resource<OtherInfoDto>> = flow{
        try{
            Log.e("error targetId",targetId.toString())
            emit(Resource.Loading())
            val token = "Bearer ${dataStore.data.first().accessToken}"
            Log.e("error token",token)

            val followOrCancelResponse = repository.getOtherInfo(accessToken = token, targetId = targetId)
            emit(
                Resource.Success(
                data = followOrCancelResponse,
                code = followOrCancelResponse.code,
                message = followOrCancelResponse.message
            )
            )
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorCode = e.response()?.errorBody()?.getErrorCode()
            errorCode?.let {
                emit(
                    Resource.Error(
                        message = errorBody!!,
                        code = errorCode
                    )
                )
                return@flow
            }
            emit(Resource.Error(message = e.message ?: "unexpected http error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}