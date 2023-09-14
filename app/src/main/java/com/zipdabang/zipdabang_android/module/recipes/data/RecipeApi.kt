package com.zipdabang.zipdabang_android.module.recipes.data

import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.module.comment.data.remote.EditCommentContent
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentContent
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentDto
import com.zipdabang.zipdabang_android.module.comment.data.remote.RecipeCommentDto
import com.zipdabang.zipdabang_android.module.detail.recipe.data.RecipeDetailDto
import com.zipdabang.zipdabang_android.module.recipes.data.banner.RecipeBannerDto
import com.zipdabang.zipdabang_android.module.recipes.data.category.RecipeCategoryDto
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewItemsDto
import com.zipdabang.zipdabang_android.module.recipes.data.recipe_list.RecipeListDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {

    @GET("members/recipes/banners")
    suspend fun getRecipeBanners(
        @Header("Authorization") accessToken: String,
    ): RecipeBannerDto?

    // 레시피 카테고리 이미지와 타이틀
    @GET("members/recipes/categories")
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
    ): RecipeListDto?

    @GET("members/recipes/categories/{categoryId}")
    suspend fun getRecipeListByCategory(
        @Header("Authorization") accessToken: String,
        @Path("categoryId") categoryId: Int,
        @Query("order") order: String,
        @Query("pageIndex") pageIndex: Int
    ): RecipeListDto

    //----------------------------------------------------------------------------------------------
    @GET("members/recipes/{recipeId}")
    suspend fun getRecipeDetail(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId: Int
    ): RecipeDetailDto

    // ---------------------------------------------------------------------------------------------
    @GET("members/recipes/{recipeId}/comments")
    suspend fun getRecipeComments(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId: Int,
        @Query("pageIndex") pageIndex: Int
    ): RecipeCommentDto

    @POST("members/recipes/{recipeId}/comments")
    suspend fun submitRecipeComment(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId: Int,
        @Body content: PostCommentContent
    ): PostCommentDto

    @DELETE("members/recipes/{recipeId}/{commentId}")
    suspend fun deleteRecipeComment(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId: Int,
        @Path("commentId") commentId: Int
    ): ResponseBody<String?>

    @PATCH("members/recipes/{recipeId}/{commentId}")
    suspend fun editRecipeComment(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId: Int,
        @Path("commentId") commentId: Int,
        @Body comment: EditCommentContent
    ): PostCommentDto
}