package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoEditResult
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoProfileRequest
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PatchUserInfoProfileUseCase @Inject constructor(
    private val repository : DrawerRepository
) {
    operator fun invoke(accessToken : String, userInfoProfile : UserInfoProfileRequest) : Flow<Resource<UserInfoEditResult>> = flow{
        try{
            emit(Resource.Loading())
            val profileResponse = repository.patchUserInfoProfile(accessToken = accessToken, userInfoProfile = userInfoProfile)
            emit(Resource.Success(
                data = profileResponse.result,
                code = profileResponse.code,
                message = profileResponse.message
            ))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}