package com.zipdabang.zipdabang_android.module.recipes.data

import com.zipdabang.zipdabang_android.module.recipes.data.banner.RecipeBannerDto
import com.zipdabang.zipdabang_android.module.recipes.data.category.RecipeCategoryDto
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewItemsDto
import com.zipdabang.zipdabang_android.module.recipes.data.recipe_list.RecipeListDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {

    // TODO 레시피 메뉴 배너
    // TODO url 수정하기
    @GET("/banners")
    suspend fun getRecipeBanners(
        @Header("Authorization") accessToken: String,
    ): RecipeBannerDto

    // 레시피 카테고리 이미지와 타이틀
    @GET("categories")
    suspend fun getRecipeCategory(
        @Header("Authorization") accessToken: String
    ): RecipeCategoryDto

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

    //----------------------------------------------------------------------------------------------

    @GET("members/recipes/types")
    suspend fun getRecipeListByOwnerType(
        @Header("Authorization") accessToken: String,
        @Query("writtenby") ownerType: String,
        @Query("order") order: String,
        @Query("pageIndex") pageIndex: Int
    ): RecipeListDto

    @GET("members/recipes/categories/{categoryId}")
    suspend fun getRecipeListByCategory(
        @Header("Authorization") accessToken: String,
        @Path("categoryId") categoryId: Int,
        @Query("order") order: String,
        @Query("pageIndex") pageIndex: Int
    ): RecipeListDto
}