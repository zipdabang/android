package com.zipdabang.zipdabang_android.module.my.data.remote

import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.module.comment.data.remote.UserBlockDto
import com.zipdabang.zipdabang_android.module.my.data.remote.followorcancel.FollowOrCancelDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.SearchFollowingDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search.SearchFollowersDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete.CompleteRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherInfoDto
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipePreviewDto
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteBeveragesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteTempResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.signout.SignOutResponseDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MyApi {
    @POST("members/logout")
    suspend fun signOut(
        @Header("Authorization") accessToken: String
    ): SignOutResponseDto

    @Multipart
    @POST("members/recipes")
    suspend fun postRecipe(
        @Header("Authorization") accessToken: String,
        @Part("content") content: RequestBody,
        @Part stepImages: List<MultipartBody.Part>,
        @Part thumbnail: MultipartBody.Part
    ): RecipeWriteResponse

    @Multipart
    @POST("members/recipes/temp")
    suspend fun postRecipeTemp(
        @Header("Authorization") accessToken: String,
        @Part("content") content: RequestBody,
        @Part stepImages: List<MultipartBody.Part>?,
        @Part thumbnail: MultipartBody.Part?
    ) : RecipeWriteTempResponse

    @GET("members/recipes/owner")
    suspend fun getMyCompleteRecipes(
        @Header("Authorization") accessToken: String,
        @Query("pageIndex") pageIndex : Int
    ) : CompleteRecipesResponse

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

    @GET("members/followings-nickname")
    suspend fun getSearchFollowings(
        @Header("Authorization") accessToken: String,
        @Query("page") page : Int,
        @Query("nickname") nickname : String
        ): SearchFollowingDto

    @GET("members/followers-nickname")
    suspend fun getSearchFollowers(
        @Header("Authorization") accessToken: String,
        @Query("page") page : Int,
        @Query("nickname") nickname : String
    ): SearchFollowersDto

    @GET("members/selfMyZipdabang")
    suspend fun getMyInfo(
        @Header("Authorization") accessToken: String
    ) : MyInfoResponse

    @GET("members/recipes/owner")
    suspend fun getMyInfoRecipes(
        @Header("Authorization") accessToken: String,
        @Query("pageIndex") pageIndex : Int
    ) : MyInfoRecipesResponse

    @GET("/categories")
    suspend fun getRecipeWriteBeverages(
        @Header("Authorization") accessToken: String
    ) : RecipeWriteBeveragesResponse

    @DELETE("members/unblock")
    suspend fun cancelUserBlock(
        @Header("Authorization") accessToken: String,
        @Query("blocked") userId: Int
    ): ResponseBody<UserBlockDto?>
}