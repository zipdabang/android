package com.zipdabang.zipdabang_android.module.login.use_case

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.module.login.data.AuthBody
import com.zipdabang.zipdabang_android.module.login.domain.Auth
import com.zipdabang.zipdabang_android.module.login.domain.AuthRepository
import com.zipdabang.zipdabang_android.module.login.mapper.toAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAuthResultUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(body: AuthBody, platform: String): Flow<Resource<Auth>> = flow {
        try {
            emit(Resource.Loading())
            val authResult = repository.getAuthResult(body = body, platform = platform).toAuth()
            emit(
                Resource.Success(
                    data = authResult,
                    code = authResult.code,
                    message = ResponseCode.getMessageByCode(authResult.code)
                )
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error("couldn't reach server, check internet connection"))
        }

    }
}

/*

class GetGoogleAuthResultUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(body: AuthBody): Flow<Resource<Auth>> = flow {
        try {
            emit(Resource.Loading())
            val authResult = repository.getAuthResult(body, "google").toAuth()
            emit(
                Resource.Success(
                    data = authResult,
                    code = authResult.code,
                    message = ResponseCode.getMessageByCode(authResult.code)
                )
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error("couldn't reach server, check internet connection"))
        }

    }
}*/
