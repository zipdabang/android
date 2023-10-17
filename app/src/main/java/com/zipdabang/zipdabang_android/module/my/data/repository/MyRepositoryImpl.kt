package com.zipdabang.zipdabang_android.module.my.data.repository

import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.signout.SignOutResponseDto
import com.zipdabang.zipdabang_android.module.my.data.remote.followorcancel.FollowOrCancelDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowDto
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingDto
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoRecipesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MyInfoResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherInfoDto
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipePreviewDto
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteBeveragesResponse
import com.zipdabang.zipdabang_android.module.my.data.remote.recipewrite.RecipeWriteTempResponse
import com.zipdabang.zipdabang_android.module.my.domain.repository.MyRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val api : MyApi
) : MyRepository{
    override suspend fun signOut(accessToken: String, ): SignOutResponseDto {
        return api.signOut(accessToken)
    }

    override suspend fun postRecipe(
        accessToken: String,
        content: RequestBody,
        thumbnail: MultipartBody.Part,
        stepImages: List<MultipartBody.Part>
    ): RecipeWriteResponse {
        return api.postRecipe(accessToken, content, stepImages, thumbnail)
    }


    override suspend fun getFollow(accessToken: String, page: Int): FollowDto {
       return api.getFollowings(accessToken, page)
    }

    override suspend fun getFollowing(accessToken: String, page: Int): FollowingDto {
       return api.getFollowers(accessToken, page)
    }

    override suspend fun postFollowOrCancel(accessToken: String, targetId: Int): FollowOrCancelDto {
       return api.postFollowOrCancel(accessToken,targetId)
    }

    override suspend fun getOtherInfo(accessToken: String, targetId: Int): OtherInfoDto {
        return api.getOtherInfo(accessToken,targetId)
    }

    override suspend fun getOtherRecipePreview(accessToken: String, memberId: Int): OtherRecipePreviewDto {
        return api.getOtherPreviewRecipe(accessToken,memberId)
    }

    override suspend fun getMyInfo(accessToken: String): MyInfoResponse {
        return api.getMyInfo(accessToken)
    }

    override suspend fun getMyInfoRecipes(
        accessToken: String,
        pageIndex: Int
    ): MyInfoRecipesResponse {
        return api.getMyInfoRecipes(accessToken, pageIndex)
    }

    override suspend fun getRecipeWriteBeverages(accessToken: String): RecipeWriteBeveragesResponse {
        return api.getRecipeWriteBeverages(accessToken)
    }

    override suspend fun postRecipeTemp(
        accessToken: String,
        content: RequestBody,
        thumbnail: MultipartBody.Part?,
        stepImages: List<MultipartBody.Part>?
    ): RecipeWriteTempResponse {
        return api.postRecipeTemp(accessToken, content, stepImages, thumbnail)
    }
}