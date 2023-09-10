package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeveragesResult
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBeveragesUseCase @Inject constructor(
    private val repositoy : SignUpRepository,
    private val repositoryDrawer: DrawerRepository,
){
    operator fun invoke() : Flow<Resource<BeveragesResult>> = flow{
        try {
            emit(Resource.Loading())
            val preferencesForSignup = repositoy.getBeverages()
            val preferencesForDrawer = repositoy.getBeverages()
            emit(Resource.Success(
                data = preferencesForSignup.result,
                code = preferencesForSignup.code,
                message = preferencesForSignup.message,
            ))
            emit(Resource.Success(
                data = preferencesForDrawer.result,
                code = preferencesForDrawer.code,
                message = preferencesForDrawer.message,
            ))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}