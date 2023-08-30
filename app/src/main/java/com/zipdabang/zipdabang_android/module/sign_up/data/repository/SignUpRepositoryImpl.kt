package com.zipdabang.zipdabang_android.module.sign_up.data.repository

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.AuthResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeveragesResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.InfoRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.InfoResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.SignUpApi
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.TermsResponse
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val api : SignUpApi
) : SignUpRepository {
    override suspend fun getTerms() : TermsResponse {
        return api.getTerms()
    }

    override suspend fun postPhoneSms(phoneRequest: PhoneRequest): AuthResponse {
        return api.postPhoneSms(phoneRequest)
    }

    override suspend fun postPhoneAuth(authRequest: AuthRequest): AuthResponse {
        return api.postPhoneAuth(authRequest)
    }

    override suspend fun getNickname(nickname: String): NicknameResponse {
        return api.getNickname(nickname)
    }


    override suspend fun getBeverages(): BeveragesResponse {
        return api.getBeverages()
    }

    override suspend fun postUserInfo(social: String, infoRequest: InfoRequest): InfoResponse {
        return api.postUserInfo(social, infoRequest)
    }

}