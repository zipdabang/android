package com.zipdabang.zipdabang_android.module.my.data.remote

import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.module.comment.data.remote.UserBlockDto
import com.zipdabang.zipdabang_android.module.my.data.remote.followorcancel.FollowOrCancelDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.SearchFollowingDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search.SearchFollowersDto
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete.CompleteRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.completewithimage.CompleteRecipesWithImgResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.like.LikeRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.like.PostLikeResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.preview.CompleteRecipesWithImgPreviewResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.scrap.PostScrapResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.scrap.ScrapRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.temp.TempRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherInfoDto
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipeListDto
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipePreviewDto
import com.zipdabang.zipdabang_android.module.my.data.remote.recipedelete.DeleteRecipeResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.complete.GetCompleteRecipeResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.temp.GetTempRecipeResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.PostSaveRecipeRequest
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteBeveragesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteTempResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.signout.SignOutResponseDto
import com.zipdabang.zipdabang_android.module.my.data.remote.userreport.UserReportDto
import com.zipdabang.zipdabang_android.module.my.data.remote.userreport.UserReportResult
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








    // 좋아요 목록 불러오기
    @GET("members/likeRecipes")
    suspend fun getLikeRecipes(
        @Header("Authorization") accessToken: String,
        @Query("page") pageIndex : Int
    ) : LikeRecipesResponse

    // 스크랩 목록 불러오기
    @GET("members/scrapRecipes")
    suspend fun getScrapRecipes(
        @Header("Authorization") accessToken: String,
        @Query("page") pageIndex : Int
    ) : ScrapRecipesResponse

    // 내 레시피 목록 불러오기
    @GET("members/recipes/owner/")
    suspend fun getMyCompleteRecipesWithImg(
        @Header("Authorization") accessToken: String,
        @Query("pageIndex") pageIndex : Int
    ) : CompleteRecipesWithImgResponse

    // 내 레시피 미리보기 목록
    @GET("members/recipes/owner/preview/")
    suspend fun getMyCompleteRecipesWithImgPreview(
        @Header("Authorization") accessToken: String
    ) : CompleteRecipesWithImgPreviewResponse



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
    @GET("members/recipes/{recipeId}")
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
        @Body categoryId : PostSaveRecipeRequest
    ) : RecipeWriteResponse

    // 임시저장 삭제
    @DELETE("members/recipes/temp/{tempId}/")
    suspend fun deleteTempRecipe(
        @Header("Authorization") accessToken: String,
        @Path("tempId") tempId : Int
    ) : DeleteRecipeResponse



    // 업로드 수정
    @Multipart
    @PATCH("members/recipes/{recipeId}")
    suspend fun patchCompleteRecipe(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId : Int,
        @Part("content") content: RequestBody,
        @Part stepImages: List<MultipartBody.Part>?,
        @Part thumbnail: MultipartBody.Part?
    ) : RecipeWriteResponse
    // 업로드 삭제
    @DELETE("members/recipes/{recipeId}")
    suspend fun deleteCompleteRecipe(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId : Int,
    ) : DeleteRecipeResponse

    // 좋아요/취소
    @POST("members/recipes/{recipeId}/likes")
    suspend fun postLike(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId : Int,
    ) : PostLikeResponse
    // 스크랩/취소
    @POST("members/recipes/{recipeId}/scrap")
    suspend fun postScrap(
        @Header("Authorization") accessToken: String,
        @Path("recipeId") recipeId : Int,
    ) : PostScrapResponse


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

    @GET("members/recipes/owner/{memberId}")
    suspend fun getOtherRecipeList(
        @Header("Authorization") accessToken: String,
        @Path("memberId") memberId : Int,
        @Query("pageIndex") page : Int,
        ) : OtherRecipeListDto
    @POST("members/block")
    suspend fun userBlock(
        @Header("Authorization") accessToken: String,
        @Query("blocked") userId: Int
    ): ResponseBody<UserBlockDto?>
    @POST("members/report/{targetId}")
    suspend fun userReport(
        @Header("Authorization") accessToken: String,
        @Path(value = "targetId") targetId: Int
    ): UserReportDto

}