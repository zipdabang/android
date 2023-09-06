package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostPhoneSmsUseCase @Inject constructor(
    private val repository : SignUpRepository,
    private val repositoryDrawer : DrawerRepository
) {
    operator fun invoke(phoneNumber : PhoneRequest) : Flow<Resource<AuthResponse>> = flow{
        try{
            emit(Resource.Loading())
            val phoneSmsResponse = repository.postPhoneSms(phoneRequest = phoneNumber)
            val phoneSmsResponseDrawer = repositoryDrawer.postPhoneSms(phoneRequest = phoneNumber)
            emit(
                Resource.Success(
                    data = phoneSmsResponse,
                    code = phoneSmsResponse.code,
                    message = phoneSmsResponse.message
                )

            )
            emit(
                Resource.Success(
                    data = phoneSmsResponseDrawer,
                    code = phoneSmsResponseDrawer.code,
                    message = phoneSmsResponseDrawer.message
                )
            )
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}