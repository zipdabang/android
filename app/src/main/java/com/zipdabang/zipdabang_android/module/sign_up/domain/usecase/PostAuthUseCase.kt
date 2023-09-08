package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import android.util.Log
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthResponse
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostAuthUseCase @Inject constructor(
    private val repository: SignUpRepository,
    private val repositoryDrawer : DrawerRepository,
) {
    operator fun invoke(authRequest : AuthRequest) : Flow<Resource<AuthResponse>> = flow {
        try {
            emit(Resource.Loading())
            val authResponse = repository.postPhoneAuth(authRequest = authRequest)
            val authResponseDrawer = repositoryDrawer.postPhoneAuth(authRequest = authRequest)
            emit(
                Resource.Success(
                    data = authResponse,
                    code = authResponse.code,
                    message = authResponse.message,
                )
            )
            emit(
                Resource.Success(
                    data = authResponseDrawer,
                    code = authResponseDrawer.code,
                    message = authResponseDrawer.message,
                )
            )
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
            //Log.e("phonenumber-usecase", "HttpException")
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            //Log.e("phonenumber-usecase", "IOException")
        }
    }
}