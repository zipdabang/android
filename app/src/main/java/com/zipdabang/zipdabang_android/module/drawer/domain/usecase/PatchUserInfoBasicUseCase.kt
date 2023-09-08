package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoBasicRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoEditResult
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PatchUserInfoBasicUseCase @Inject constructor(
    private val repository : DrawerRepository
){
    operator fun invoke(accessToken : String, userInfoBasic : UserInfoBasicRequest) : Flow<Resource<UserInfoEditResult>> = flow{
        try {
            emit(Resource.Loading())
            val basicResponse = repository.patchUserInfoBasic(accessToken = accessToken, userInfoBasic = userInfoBasic)
            emit(Resource.Success(
                data = basicResponse.result,
                code = basicResponse.code,
                message = basicResponse.message,
            ))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}