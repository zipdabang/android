package com.zipdabang.zipdabang_android.module.sign_up.data.repository

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeveragesDto
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.SignUpApi
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.TermsDto
import com.zipdabang.zipdabang_android.module.sign_up.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val api : SignUpApi
) : SignUpRepository {
    override suspend fun getTerms() : TermsDto {
        return api.getTerms()
    }

    override suspend fun getNickname(nickname: String): NicknameResponse {
        return api.getNickname(nickname)
    }


    override suspend fun getBeverages(): BeveragesDto {
        return api.getBeverages()
    }

}