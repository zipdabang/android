package com.zipdabang.zipdabang_android.module.recipes.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.login.use_case.GetTempLoginResultUseCase
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggle
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.mapper.toPreferenceToggle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class ToggleLikeItemUseCase @Inject constructor(
    private val recipeListRepository: RecipeListRepository,
    private val tokenDataStore: DataStore<Token>
) {
    companion object {
        const val TAG = "ToggleLikeItemUseCase"
    }
    suspend operator fun invoke(
        recipeId: Int
    ): Boolean =
        try {
            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)

            val remoteResult = recipeListRepository
                .toggleLikeRemote(accessToken = accessToken, recipeId = recipeId)
            remoteResult.isSuccess && remoteResult.result != null && remoteResult.code == 2000
        } catch (e: HttpException) {
            false
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }


}