package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneResponse
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPhoneSmsUseCase @Inject constructor(
    private val repository : SignUpRepository
) {
    operator fun invoke(phone : PhoneRequest) : Flow<Resource<PhoneResponse>> = flow{
        try{
            emit(Resource.Loading())
            val phoneSmsResponse = repository.postPhoneSms(phoneRequest = phone)
            emit(
                Resource.Success(
                    data = phoneSmsResponse,
                    code = phoneSmsResponse.code,
                    message = phoneSmsResponse.message
                )
            )
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}