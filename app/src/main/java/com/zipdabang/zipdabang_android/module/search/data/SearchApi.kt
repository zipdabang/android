package com.zipdabang.zipdabang_android.module.search.data

import com.zipdabang.zipdabang_android.module.search.data.dto.recipecategory.SearchRecipeCategoryDto
import com.zipdabang.zipdabang_android.module.search.data.dto.searchpreview.SearchDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApi {

    @GET("/members/recipes/search/preview")
    suspend fun getSearchPreview(
        @Header("Authorization") accessToken: String,
        @Query("keyword") keyWord : String
    ) : SearchDto?

    @GET("/members/recipes/search/{categoryId}")
    suspend fun getSearchRecipeCategory(
        @Header("Authorization") accessToken: String,
        @Path("categoryId") categoryId : Int,
        @Query("keyword") keyWord : String,
        @Query("pageIndex") pageIndex : Int
    ) : SearchRecipeCategoryDto



}