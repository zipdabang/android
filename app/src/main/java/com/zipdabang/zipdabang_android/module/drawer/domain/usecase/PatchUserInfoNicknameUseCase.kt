package com.zipdabang.zipdabang_android.module.drawer.domain.usecase

import com.kakao.sdk.user.model.AccessTokenInfo
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoEditResponse
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoEditResult
import com.zipdabang.zipdabang_android.module.drawer.data.remote.userinfodto.UserInfoNicknameRequest
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PatchUserInfoNicknameUseCase @Inject constructor(
    private val repository: DrawerRepository
) {
    operator fun invoke(accessToken : String, userInfoNickname : UserInfoNicknameRequest) : Flow<Resource<UserInfoEditResult>> = flow {
        try {
            emit(Resource.Loading())
            val nicknameResponse = repository.patchUserInfoNickname(accessToken = accessToken, userInfoNickname = userInfoNickname)
            emit(Resource.Success(
                data = nicknameResponse.result,
                code = nicknameResponse.code,
                message = nicknameResponse.message
            ))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}