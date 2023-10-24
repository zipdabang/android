package com.zipdabang.zipdabang_android.module.recipes.use_case

import android.util.Log
import androidx.datastore.core.DataStore
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.mapper.toHotRecipes
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GetOwnerItemCountUseCase @Inject constructor(
    private val tokens: DataStore<Token>,
    private val repository: RecipeListRepository
) {
    operator fun invoke(ownerType: String) = flow<Int> {
        try {
            val accessToken = "Bearer ${tokens.data.first().accessToken}"
            val result = repository
                .getItemCountByOwner(accessToken, ownerType)
                .result ?: 0

            emit(result)

        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(0)
        }
    }
}