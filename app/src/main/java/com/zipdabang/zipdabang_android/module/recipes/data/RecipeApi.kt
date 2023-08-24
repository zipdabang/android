package com.zipdabang.zipdabang_android.module.recipes.data

import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewItemsDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {
    // 레시피 미리보기(작성타입별 5개)
    @GET("members/recipes/types/preview")
    suspend fun getRecipePreview(
        @Header("Authorization") accessToken: String,
        @Query("writtenby") ownerType: String
    ): RecipePreviewItemsDto

    // 좋아요 토글
    @POST("members/recipes/{recipeId}/likes")
    suspend fun toggleLike(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId: Int
    ): PreferenceResultDto

    // 스크랩 토글
    @POST("members/recipes/{recipeId}/scrap")
    suspend fun toggleScrap(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId: Int
    ): PreferenceResultDto
}