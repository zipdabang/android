package com.zipdabang.zipdabang_android.module.sign_up.data.repository

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeveragesResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneResponse
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

    override suspend fun postPhoneSms(phoneRequest: String): PhoneResponse {
        return api.postPhoneSms(phoneRequest)
    }

    override suspend fun getNickname(nickname: String): NicknameResponse {
        return api.getNickname(nickname)
    }


    override suspend fun getBeverages(): BeveragesResponse {
        return api.getBeverages()
    }

}