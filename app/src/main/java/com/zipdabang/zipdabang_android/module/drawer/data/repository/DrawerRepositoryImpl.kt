package com.zipdabang.zipdabang_android.module.drawer.data.repository

import com.zipdabang.zipdabang_android.module.drawer.data.remote.DrawerApi
import com.zipdabang.zipdabang_android.module.drawer.data.remote.UserInfoResponse
import com.zipdabang.zipdabang_android.module.drawer.domain.repository.DrawerRepository
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import javax.inject.Inject

class DrawerRepositoryImpl @Inject constructor(
    private val api : DrawerApi
) : DrawerRepository{
    override suspend fun getUserInfo(accessToken : String): UserInfoResponse {
        return api.getUserInfo(accessToken = accessToken)
    }

    override suspend fun getNickname(nickname: String): NicknameResponse {
        return api.getNickname(nickname = nickname)
    }

    override suspend fun postPhoneSms(phoneRequest: PhoneRequest): AuthResponse {
        return api.postPhoneSms(phoneRequest = phoneRequest)
    }

    override suspend fun postPhoneAuth(authRequest: AuthRequest): AuthResponse {
       return api.postPhoneAuth(authRequest = authRequest)
    }

}