package com.zipdabang.zipdabang_android.module.my.domain.repository

import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.signout.SignOutResponseDto
import com.zipdabang.zipdabang_android.module.my.data.remote.followorcancel.FollowOrCancelDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingDto
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherInfoDto
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipePreviewDto
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteBeveragesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteTempResponse

interface MyRepository {
    suspend fun signOut(accessToken: String): SignOutResponseDto
    suspend fun postRecipe(accessToken: String, content: RequestBody, thumbnail : MultipartBody.Part, stepImages: List<MultipartBody.Part> ) : RecipeWriteResponse
    suspend fun getFollow(accessToken: String, page : Int) : FollowDto
    suspend fun getFollowing(accessToken: String, page : Int) : FollowingDto
    suspend fun postFollowOrCancel(accessToken: String, targetId : Int) : FollowOrCancelDto
    suspend fun getOtherInfo(accessToken: String, targetId : Int) : OtherInfoDto
    suspend fun getOtherRecipePreview(accessToken: String, memberId : Int) : OtherRecipePreviewDto
    suspend fun getMyInfo(accessToken: String) : MyInfoResponse
    suspend fun getMyInfoRecipes(accessToken: String, pageIndex : Int) : MyInfoRecipesResponse
    suspend fun getRecipeWriteBeverages(accessToken: String) : RecipeWriteBeveragesResponse
    suspend fun postRecipeTemp(accessToken: String, content: RequestBody, thumbnail : MultipartBody.Part?, stepImages: List<MultipartBody.Part>? ) : RecipeWriteTempResponse
}