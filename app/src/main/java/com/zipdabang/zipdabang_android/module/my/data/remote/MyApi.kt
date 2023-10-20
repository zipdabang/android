package com.zipdabang.zipdabang_android.module.my.data.remote

import com.zipdabang.zipdabang_android.module.my.data.remote.followorcancel.FollowOrCancelDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.SearchFollowingDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search.SearchFollowersDto
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete.CompleteRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.temp.TempRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherInfoDto
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipePreviewDto
import com.zipdabang.zipdabang_android.module.my.data.remote.recipedelete.DeleteRecipeResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.complete.GetCompleteRecipeResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.complete.PatchCompleteRecipeRequestBody
import com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.temp.GetTempRecipeResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.PostTempRecipeSaveRequestBody
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteBeveragesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteTempResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.signout.SignOutResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MyApi {
    @POST("members/logout")
    suspend fun signOut(
        @Header("Authorization") accessToken: String
    ): SignOutResponseDto









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



    // 업로드 목록 가져오기
    @GET("members/recipes/owner/")
    suspend fun getMyCompleteRecipes(
        @Header("Authorization") accessToken: String,
        @Query("pageIndex") pageIndex : Int
    ) : CompleteRecipesResponse
    // 임시저장 목록 가져오기
    @GET("members/recipes/temp/")
    suspend fun getMyTempRecipes(
        @Header("Authorization") accessToken: String,
        @Query("pageIndex") pageIndex : Int
    ) : TempRecipesResponse
    // 임시저장 상세정보 가져오기
    @GET("members/recipes/temp/{tempId}/")
    suspend fun getMyTempRecipesDetail(
        @Header("Authorization") accessToken: String,
        @Path("tempId") tempId : Int
    ) : GetTempRecipeResponse
    // 업로드 상세정보 가져오기
    @GET("members/recipes/")
    suspend fun getMyCompleteRecipesDetail(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId : Int
    ) : GetCompleteRecipeResponse



    // 업로드 하기
    @Multipart
    @POST("members/recipes")
    suspend fun postRecipe(
        @Header("Authorization") accessToken: String,
        @Part("content") content: RequestBody,
        @Part stepImages: List<MultipartBody.Part>,
        @Part thumbnail: MultipartBody.Part
    ): RecipeWriteResponse

    // 임시저장 하기
    @Multipart
    @POST("members/recipes/temp")
    suspend fun postRecipeTemp(
        @Header("Authorization") accessToken: String,
        @Part("content") content: RequestBody,
        @Part stepImages: List<MultipartBody.Part>?,
        @Part thumbnail: MultipartBody.Part?
    ) : RecipeWriteTempResponse

    // 임시저장 수정 (임시저장->임시저장)
    @Multipart
    @POST("members/recipes/temp/{tempId}")
    suspend fun postTempRecipeToTemp(
        @Header("Authorization") accessToken: String,
        @Path("tempId") tempId : Int,
        @Part("content") content: RequestBody,
        @Part stepImages: List<MultipartBody.Part>?,
        @Part thumbnail: MultipartBody.Part?
    ) : RecipeWriteResponse
    // 임시저장->업로드 : 최종 등록
    @POST("members/recipes/temp/{tempId}/save")
    suspend fun postTempRecipeSave(
        @Header("Authorization") accessToken: String,
        @Path("tempId") tempId : Int,
        @Body categoryId : PostTempRecipeSaveRequestBody
    ) : RecipeWriteResponse

    // 임시저장 삭제
    @DELETE("members/recipes/temp/{tempId}/")
    suspend fun deleteTempRecipe(
        @Header("Authorization") accessToken: String,
        @Path("tempId") tempId : Int
    ) : DeleteRecipeResponse



    // 업로드 수정
    @PATCH("members/recipes/{recipeId}")
    suspend fun patchCompleteRecipe(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId : Int,
        @Body content : PatchCompleteRecipeRequestBody
    ) : RecipeWriteResponse
    // 업로드 삭제
    @DELETE("members/recipes/{recipeId}")
    suspend fun deleteCompleteRecipe(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId : Int,
    ) : DeleteRecipeResponse










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

}