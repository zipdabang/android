package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoResult
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: DrawerRepository,
) {
    operator fun invoke(accessToken : String) : Flow<Resource<UserInfoResult>> = flow{
        try{
            emit(Resource.Loading())
            val userinfo = repository.getUserInfo(accessToken = accessToken)
            emit(Resource.Success(
                data = userinfo.result,
                code = userinfo.code,
                message = userinfo.message
            ))
        } catch(e: HttpException) {
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
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}