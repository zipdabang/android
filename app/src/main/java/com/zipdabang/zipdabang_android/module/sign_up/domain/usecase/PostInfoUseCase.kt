package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.InfoRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.InfoResponse
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostInfoUseCase @Inject constructor(
    private val repository: SignUpRepository
){
    operator fun invoke(social : String, infoRequest: InfoRequest) : Flow<Resource<InfoResponse>> = flow{
        try{
            emit(Resource.Loading())
            val infoResponse = repository.postUserInfo(social = social, infoRequest=infoRequest)
            emit(
                Resource.Success(
                    data = infoResponse,
                    code = infoResponse.code,
                    message = infoResponse.message
                )
            )
        }catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}