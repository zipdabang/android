package com.zipdabang.zipdabang_android.module.drawer.domain.repository

import com.zipdabang.zipdabang_android.module.drawer.data.remote.UserInfoResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest

interface DrawerRepository {
    suspend fun getUserInfo(accessToken : String) : UserInfoResponse
    suspend fun getNickname(nickname : String) : NicknameResponse
    suspend fun postPhoneSms(phoneRequest: PhoneRequest) : AuthResponse
    suspend fun postPhoneAuth(authRequest : AuthRequest) : AuthResponse
}