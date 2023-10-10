package com.zipdabang.zipdabang_android.module.my.data.remote

import com.zipdabang.zipdabang_android.module.my.data.remote.followorcancel.FollowOrCancelDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingDto
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherInfoDto
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipePreviewDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MyApi {
    @POST("members/logout")
    suspend fun signOut(
        @Header("Authorization") accessToken: String
    ): SignOutResponseDto

    @GET("categories")
    suspend fun getCategories(
        @Header("Authorization") accessToken: String
    ): CategoriesResponse

    @POST("members/recipes")
    suspend fun postRecipe(
        @Header("Authorization") accessToken: String,
        @Body recipeWriteForm : RecipeWriteRequest
    ): RecipeWriteResponse

    @GET("members/followings")
    suspend fun getFollowings(
        @Header("Authorization") accessToken: String,
        @Query("page") page : Int
    ) : FollowDto

    @GET("members/followers")
    suspend fun getFollowers(
        @Header("Authorization") accessToken: String,
        @Query("page") page : Int
    ) : FollowingDto

    @POST("members/followings/{targetId}")
    suspend fun postFollowOrCancel(
        @Header("Authorization") accessToken: String,
        @Path(value = "targetId") targetId : Int
    ) : FollowOrCancelDto

    @GET("members/myZipdabang",)
    suspend fun getOtherInfo(
        @Header("Authorization") accessToken: String,
        @Query("targetMemberId") targetId : Int
    ) : OtherInfoDto
    @GET("members/recipes/owner/preview/{memberId}")
    suspend fun getOtherPreviewRecipe(
        @Header("Authorization") accessToken: String,
        @Path(value = "memberId") memeberId : Int
    ) : OtherRecipePreviewDto

}