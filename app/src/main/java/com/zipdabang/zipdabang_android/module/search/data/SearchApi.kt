package com.zipdabang.zipdabang_android.module.search.data

import com.zipdabang.zipdabang_android.module.search.data.dto.SearchDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchApi {

    @GET("/members/recipes/search/prieview")
    suspend fun getSearchPreview(
        @Header("Authorization") accessToken: String,
        @Query("keyword") keyWord : String
    ) : SearchDto?



}