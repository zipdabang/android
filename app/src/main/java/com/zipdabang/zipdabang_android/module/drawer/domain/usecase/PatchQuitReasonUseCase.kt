package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto.QuitDto
import com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto.QuitRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.quitdto.QuitResult
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoBasicRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoEditResult
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
    operator fun invoke(quitReason : QuitRequest) : Flow<Resource<QuitDto>> = flow{
        try {
            emit(Resource.Loading())
            val accessToken = datastore.data.first().accessToken ?: Constants.TOKEN_NULL
            val token = "Bearer " + accessToken
            Log.e("quit_log",quitReason.deregisterTypes[0])
            val basicResponse = repository.patchQuit(accessToken =token, quitReason = quitReason)
            Log.e("quit_log",basicResponse.code.toString())

            emit(
                Resource.Success(
                    data = basicResponse,
                    code = basicResponse.code,
                    message = basicResponse.message
                )
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}