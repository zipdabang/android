package com.zipdabang.zipdabang_android.module.sign_up.domain.repository

import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeveragesDto
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.TermsDto

interface SignUpRepository {
    suspend fun getTerms() : TermsDto

    suspend fun getBeverages() : BeveragesDto
}