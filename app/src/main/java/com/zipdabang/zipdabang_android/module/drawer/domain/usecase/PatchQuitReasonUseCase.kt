package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto.QuitDto
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PatchQuitReasonUseCase @Inject constructor(
    private val repository : DrawerRepository,
    private val datastore : DataStore<Token>

){
    operator fun invoke(deregisterTypes: List<String>,feedback : String) : Flow<Resource<QuitDto>> = flow{
        try {
            emit(Resource.Loading())
            val accessToken = datastore.data.first().accessToken ?: Constants.TOKEN_NULL
            val token = "Bearer " + accessToken
            val basicResponse = repository.patchQuit(accessToken =token, deregisterTypes= deregisterTypes, feedback = feedback)
            Log.e("quit_log",basicResponse.code.toString())

            emit(
                Resource.Success(
                    data = basicResponse,
                    code = basicResponse.code,
                    message = basicResponse.message
                )
            )
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                emit(
                    Resource.Error(
                        message = e.response()?.errorBody().toString(),
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