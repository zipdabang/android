package com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.my.ui.component.FollowItem
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class OtherRecipeListPagingSource(
    private val tokenDataStore : DataStore<Token>,
    private val api: MyApi,
    private val memberId : Int
) : PagingSource<Int, OtherRecipe>() {
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun load(
        params: PagingSource.LoadParams<Int>
    ): PagingSource.LoadResult<Int, OtherRecipe> {
        val currentPage = params.key ?: 1
        return try {
            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)
            Log.e("memberId",memberId.toString())
            val response = api.getOtherRecipeList(accessToken, memberId, currentPage)
            val endOfPaginationReached = response.result?.isLast
            if (response.result == null) {
                Log.e("otherRecipeList","null")

                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                Log.e("otherRecipeList",response.result.recipeList.size.toString())
                LoadResult.Page(
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
                Log.e("error in otherRecipeList Mediator", ResponseCode.getMessageByCode(errorCode))
            }
            LoadResult.Error(e)
        } catch (e: IOException) {
            Log.e(
                "error in other recipe List Mediator",
                "Couldn't reach server. Check your internet connection."
            )
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, OtherRecipe>): Int? {
        return state.anchorPosition
    }
}
