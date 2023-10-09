package com.zipdabang.zipdabang_android.module.my.domain.repository

import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteRequest
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.SignOutResponseDto
import com.zipdabang.zipdabang_android.module.my.data.remote.SignOutTokens
import com.zipdabang.zipdabang_android.module.my.data.remote.followorcancel.FollowOrCancelDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingDto
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherInfoDto
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipePreviewDto

interface MyRepository {
    suspend fun signOut(accessToken: String): SignOutResponseDto
    suspend fun postRecipe(accessToken: String, recipeWriteForm : RecipeWriteRequest) : RecipeWriteResponse
    suspend fun getFollow(accessToken: String, page : Int) : FollowDto
    suspend fun getFollowing(accessToken: String, page : Int) : FollowingDto
    suspend fun postFollowOrCancel(accessToken: String, targetId : Int) : FollowOrCancelDto
    suspend fun getOtherInfo(accessToken: String, targetId : Int) : OtherInfoDto
    suspend fun getOtherRecipePreview(accessToken: String, memberId : Int) : OtherRecipePreviewDto

}