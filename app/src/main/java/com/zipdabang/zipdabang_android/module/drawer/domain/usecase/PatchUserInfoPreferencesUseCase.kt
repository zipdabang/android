package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoEditResult
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoPreferencesRequest
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PatchUserInfoPreferencesUseCase @Inject constructor(
    private val repository : DrawerRepository
){
    operator fun invoke(accessToken : String, userInfoPreferences : UserInfoPreferencesRequest) : Flow<Resource<UserInfoEditResult>> = flow {
        try{
            emit(Resource.Loading())
            val preferencesResponse = repository.patchUserPreferences(accessToken = accessToken, userInfoPreferences = userInfoPreferences)
            emit(Resource.Success(
                data = preferencesResponse.result,
                code = preferencesResponse.code,
                message = preferencesResponse.message,
            ))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}