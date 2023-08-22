package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeveragesResult
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBeveragesUseCase @Inject constructor(
    private val repositoy : SignUpRepository
){
    operator fun invoke() : Flow<Resource<BeveragesResult>> = flow{
        try {
            emit(Resource.Loading())
            val preferences = repositoy.getBeverages()
            emit(Resource.Success(
                data = preferences.result,
                code = preferences.code,
                message = preferences.message,
            ))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}