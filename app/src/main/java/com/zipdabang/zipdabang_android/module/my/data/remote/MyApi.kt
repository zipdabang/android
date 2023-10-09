package com.zipdabang.zipdabang_android.module.my.data.remote

import com.zipdabang.zipdabang_android.module.my.data.remote.followorcancel.FollowOrCancelDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
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

    @GET("categories")
    suspend fun getCategories(
        @Header("Authorization") accessToken: String
    ): CategoriesResponse

    @Multipart
    @POST("members/recipes")
    suspend fun postRecipe(
        @Header("Authorization") accessToken: String,
        @Part ("content") content: RequestBody, // 다른 멀티파트 파트들을 정의할 수 있음
        @Part stepImages: List<MultipartBody.Part>, // 이미지를 멀티파트로 보낼 때는 MultipartBody.Part를 사용
        @Part thumbnail: MultipartBody.Part
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

}