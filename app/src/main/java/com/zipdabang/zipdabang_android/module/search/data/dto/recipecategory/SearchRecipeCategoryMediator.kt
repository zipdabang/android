package com.zipdabang.zipdabang_android.module.search.data.dto.recipecategory

import android.util.Log
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherRecipe
import com.zipdabang.zipdabang_android.module.search.data.SearchApi
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SearchRecipeCategoryPagingSource(
    private val searchApi: SearchApi,
    private val categoryId: Int,
    private val searchOrder : String,
    private val searchText : String,
    private val tokenDataStore: DataStore<Token>
) : PagingSource<Int, SearchRecipe>() {
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun load(
        params: PagingSource.LoadParams<Int>
    ): PagingSource.LoadResult<Int, SearchRecipe> {
        val currentPage = params.key ?: 1
        return try {
            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)
            Log.e("search currentPage",currentPage.toString())

            Log.e("search catergoryId",categoryId.toString())
            Log.e("searchOrder",searchOrder)
            Log.e("searchText",searchText.toString())

            val response = searchApi.getSearchRecipeCategory(accessToken,categoryId,searchText,searchOrder,currentPage)
            val endOfPaginationReached = response.result?.isLast
            if (response.result == null) {
                Log.e("searchRecipeList","null")

                PagingSource.LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                Log.e("searchRecipeList",response.result.recipeList.size.toString())
                PagingSource.LoadResult.Page(
                    data = response.result.recipeList,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached == true) null else currentPage + 1
                )

            }

        }
        catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()
            val errorCode = errorBody?.getErrorCode()
            errorCode?.let {
                Log.e("error in searchRecipeList Mediator", errorCode.toString())
            }
            PagingSource.LoadResult.Error(e)
        } catch (e: IOException) {
            Log.e(
                "error in search recipe List Mediator",
                "Couldn't reach server. Check your internet connection."
            )
            PagingSource.LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchRecipe>): Int? {
        return state.anchorPosition
    }
}
