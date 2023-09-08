package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoDetailRequest
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoEditResult
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PatchUserInfoDetailUseCase @Inject constructor(
    private val repository: DrawerRepository
) {
    operator fun invoke(accessToken : String, userInfoDetail : UserInfoDetailRequest) : Flow<Resource<UserInfoEditResult>> = flow{
        try{
            emit(Resource.Loading())
            val detailResponse = repository.patchUserInfoDetail(accessToken = accessToken, userInfoDetail = userInfoDetail)
            emit(Resource.Success(
                data = detailResponse.result,
                code = detailResponse.code,
                message = detailResponse.message
            ))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}