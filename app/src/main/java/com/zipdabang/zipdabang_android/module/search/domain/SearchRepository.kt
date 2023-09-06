package com.zipdabang.zipdabang_android.module.search.domain

import com.zipdabang.zipdabang_android.module.search.data.dto.SearchDto

interface SearchRepository {

    suspend fun getSearchPreview(token : String?, keyWord : String?) : SearchDto?
}