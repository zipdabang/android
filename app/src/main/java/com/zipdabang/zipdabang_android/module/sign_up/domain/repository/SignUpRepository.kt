package com.zipdabang.zipdabang_android.module.sign_up.domain.repository

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeveragesResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.InfoRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.InfoResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.TermsResponse

interface SignUpRepository {
    suspend fun getTerms() : TermsResponse
    suspend fun postPhoneSms(phoneRequest: PhoneRequest) : AuthResponse
    suspend fun postPhoneAuth(authRequest : AuthRequest) : AuthResponse
    suspend fun getNickname(nickname : String) : NicknameResponse
    suspend fun getBeverages() : BeveragesResponse
    suspend fun postUserInfo(social : String, infoRequest: InfoRequest) : InfoResponse
}