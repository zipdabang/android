package com.zipdabang.zipdabang_android.module.my.data.remote

import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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
}