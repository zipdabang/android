package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.drawer.data.remote.UserInfoResult
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repository: DrawerRepository,
) {
    operator fun invoke(accessToken : String) : Flow<Resource<UserInfoResult>> = flow{
        try{
            emit(Resource.Loading())
            val userinfo = repository.getUserInfo(accessToken = accessToken)
            emit(Resource.Success(
                data = userinfo.result,
                code = userinfo.code,
                message = userinfo.message
            ))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}