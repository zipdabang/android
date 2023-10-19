package com.zipdabang.zipdabang_android.module.my.domain.repository

import com.zipdabang.zipdabang_android.module.my.data.remote.followorcancel.FollowOrCancelDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.SearchFollowingDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search.SearchFollowersDto
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete.CompleteRecipesResponse
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

interface MyRepository {
    suspend fun signOut(accessToken: String): SignOutResponseDto
    suspend fun postRecipe(accessToken: String, content: RequestBody, thumbnail : MultipartBody.Part, stepImages: List<MultipartBody.Part> ) : RecipeWriteResponse
    suspend fun getFollow(accessToken: String, page : Int) : FollowDto
    suspend fun getFollowing(accessToken: String, page : Int) : FollowingDto
    suspend fun postFollowOrCancel(accessToken: String, targetId : Int) : FollowOrCancelDto
    suspend fun getOtherInfo(accessToken: String, targetId : Int) : OtherInfoDto
    suspend fun getOtherRecipePreview(accessToken: String, memberId : Int) : OtherRecipePreviewDto
    suspend fun getSearchFollowings(accessToken: String, page : Int, nickname : String) : SearchFollowingDto
    suspend fun getSearchFollowers(accessToken: String, page : Int,nickname: String) : SearchFollowersDto
    suspend fun getMyInfo(accessToken: String) : MyInfoResponse
    suspend fun getMyInfoRecipes(accessToken: String, pageIndex : Int) : MyInfoRecipesResponse
    suspend fun getRecipeWriteBeverages(accessToken: String) : RecipeWriteBeveragesResponse
    suspend fun postRecipeTemp(accessToken: String, content: RequestBody, thumbnail : MultipartBody.Part?, stepImages: List<MultipartBody.Part>? ) : RecipeWriteTempResponse
    suspend fun getMyCompleteRecipes(accessToken: String, pageIndex : Int) : CompleteRecipesResponse
    suspend fun getMyTempRecipesDetail(accessToken: String, tempId : Int) : GetTempRecipeResponse
    suspend fun getMyCompleteRecipesDetail(accessToken: String, recipeId : Int ) : GetCompleteRecipeResponse
    suspend fun postTempRecipeToTemp(accessToken: String, tempId: Int, content : RequestBody, thumbnail : MultipartBody.Part?, stepImages: List<MultipartBody.Part>?) : RecipeWriteResponse
    suspend fun postTempRecipeSave(accessToken: String, tempId: Int, categoryId : PostTempRecipeSaveRequestBody) : RecipeWriteResponse
    suspend fun deleteTempRecipe(accessToken: String, tempId: Int) : DeleteRecipeResponse
    suspend fun patchCompleteRecipe(accessToken: String, recipeId: Int, content : PatchCompleteRecipeRequestBody) : RecipeWriteResponse
    suspend fun deleteCompleteRecipe(accessToken: String, recipeId: Int) : DeleteRecipeResponse
}