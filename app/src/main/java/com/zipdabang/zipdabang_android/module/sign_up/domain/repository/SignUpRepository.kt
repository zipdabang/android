package com.zipdabang.zipdabang_android.module.sign_up.domain.repository

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeveragesDto
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.NicknameResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneRequest
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.PhoneResponse
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.TermsDto

interface SignUpRepository {
    suspend fun getTerms() : TermsDto
    suspend fun postPhoneSms(phoneRequest: PhoneRequest) : PhoneResponse
    suspend fun getNickname(nickname : String) : NicknameResponse
    suspend fun getBeverages() : BeveragesDto
}