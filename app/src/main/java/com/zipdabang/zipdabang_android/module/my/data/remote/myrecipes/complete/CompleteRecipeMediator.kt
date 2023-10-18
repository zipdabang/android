package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.complete

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.common.getErrorCode
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CompleteRecipeMediator @Inject constructor(
    private val myApi: MyApi,
    private val paging3Database: Paging3Database,
    private val tokenDataStore : DataStore<Token>
) : RemoteMediator<Int, CompleteRecipe>(){

    private val myCompleteRecipesDao = paging3Database.completeRecipesDao()
    private val RemoteKeyDao = paging3Database.RemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CompleteRecipe>
    ): MediatorResult {
        return try {
            // 현재 페이지
            val currentPage = when (loadType) {
                LoadType.REFRESH ->{
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND ->{
                    val remoteKeys = getRemotekeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?:
                    return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }
                LoadType.APPEND ->{
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?:
                         return MediatorResult.Success(
                             endOfPaginationReached = remoteKeys != null
                         )
                    nextPage
                }
            }

            //api 실행
            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)
            val responseMapList = mutableListOf<CompleteRecipe>()
            var response : CompleteRecipesResponse? = null

            try {
                Log.e("my_completerecipes_api 실행전", currentPage.toString())
                Log.e("my_completerecipes_api 실행전", accessToken)
                Log.e("my_completerecipes_api 실행", "api 실행함")
                response = myApi.getMyCompleteRecipes(
                    accessToken = accessToken,
                    pageIndex = currentPage
                )

                val data = response.result!!.recipeList
                Log.e("my_completerecipes_api 실행", data.toString())
                data.forEach{ recipes ->
                    responseMapList.add(
                        CompleteRecipe(
                            categoryId = recipes.categoryId,
                            comments = recipes.comments,
                            createdAt = recipes.createdAt,
                            isLiked = recipes.isLiked,
                            isScrapped = recipes.isScrapped,
                            likes = recipes.likes,
                            nickname = recipes.nickname,
                            recipeId = recipes.recipeId,
                            recipeName = recipes.recipeName,
                            scraps = recipes.scraps,
                            thumbnailUrl = recipes.thumbnailUrl,
                            updatedAt = recipes.updatedAt
                        )
                    )
                }

            }
            catch (e: HttpException){
                val errorBody = e.response()?.errorBody()
                val errorCode = errorBody?.getErrorCode()

                if (errorBody != null) {
                    Log.e("my_completerecipes_api 실패1", "errorBody : ${e.response()?.code()}")
                } else {
                    Log.e("my_completerecipes_api 실패1", "errorBody : null")
                }

                errorCode?.let{
                    if(errorCode==4055){
                        Log.e("my_completerecipes_api 실패2", errorBody.toString())
                        myCompleteRecipesDao.deleteItems()
                        RemoteKeyDao.deleteRemoteKeys()
                        MediatorResult.Success(endOfPaginationReached = true)
                    }
                }
            }
            catch (e:IOException){
                Log.e("my_completerecipes_api 실패2", "왜지")
            }
            catch (e: Exception){
                if (e is CancellationException){
                    throw e
                }
                Log.e("my_completerecipes_api 실패3", "뭐지")
            }


            val endOfPaginationReached = response?.result!!.isLast
            val prevPage = if (currentPage == 1) null else currentPage -1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            paging3Database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    myCompleteRecipesDao.deleteItems()
                    RemoteKeyDao.deleteRemoteKeys()
                }
                val keys = responseMapList.map {items ->
                    RemoteKeys(
                        id = items.recipeId,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                myCompleteRecipesDao.addItems(responseMapList)
                RemoteKeyDao.addAllRemoteKeys(keys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached!!)
        }
        catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }



    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state : PagingState<Int, CompleteRecipe>
    ) : RemoteKeys? {
        //state.anchorposition은 현재 화면에 보이는 첫 번째 아이템의 포지션
        return state.anchorPosition?.let {position ->
            state.closestItemToPosition(position)?.recipeId?.let{ recipeId ->
                RemoteKeyDao.getRemoteKeys(id= recipeId)
            }
        }
    }

    private suspend fun getRemotekeyForFirstItem(
        state : PagingState<Int, CompleteRecipe>
    ) : RemoteKeys? {
        return state.pages.firstOrNull{ it.data.isNotEmpty() }?.data?.firstOrNull()?.let{item ->
            RemoteKeyDao.getRemoteKeys(id = item.recipeId)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state : PagingState<Int, CompleteRecipe>
    ) : RemoteKeys? {
        return state.pages.lastOrNull{ it.data.isNotEmpty() }?.data?.lastOrNull()?.let{item ->
            RemoteKeyDao.getRemoteKeys(id = item.recipeId)
        }
    }
}