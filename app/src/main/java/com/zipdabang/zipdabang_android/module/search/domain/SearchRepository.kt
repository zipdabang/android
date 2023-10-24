package com.zipdabang.zipdabang_android.module.search.domain

import com.zipdabang.zipdabang_android.module.search.data.dto.recipecategory.SearchCountDto
import com.zipdabang.zipdabang_android.module.search.data.dto.recipecategory.SearchRecipeCategoryDto
import com.zipdabang.zipdabang_android.module.search.data.dto.searchpreview.SearchDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchRepository {

    suspend fun getSearchPreview(token : String?, keyWord : String?) : SearchDto

    suspend fun getSearchRecipeCategoryPreview(accessToken: String, categoryId : Int,order : String, keyWord : String, pageIndex : Int
    ) : SearchRecipeCategoryDto?
    suspend fun getSearchCount(accessToken: String, categoryId : Int,keyWord : String
    ) : SearchCountDto


}