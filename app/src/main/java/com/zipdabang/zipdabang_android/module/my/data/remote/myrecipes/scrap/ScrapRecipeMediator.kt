package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.scrap

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
import com.zipdabang.zipdabang_android.core.navigation.MyScreen
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.temp.TempRecipe
import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.temp.TempRecipesResponse
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class ScrapRecipeMediator @Inject constructor(
    private val myApi: MyApi,
    private val paging3Database: Paging3Database,
    private val tokenDataStore : DataStore<Token>
) : RemoteMediator<Int, ScrapRecipe>()  {

    private val scrapRecipesDao = paging3Database.scrapRecipesDao()
    private val RemoteKeyDao = paging3Database.RemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ScrapRecipe>
    ): MediatorResult {
        return try{
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

            // api 실행
            val accessToken = ("Bearer " + tokenDataStore.data.first().accessToken)
            val responseMapList = mutableListOf<ScrapRecipe>()
            var response : ScrapRecipesResponse? = null

            try{
                Log.e("my_scraprecipes_api 실행전", currentPage.toString())
                Log.e("my_scraprecipes_api 실행전", accessToken)
                Log.e("my_scraprecipes_api 실행", "api 실행함")
                response = myApi.getScrapRecipes(
                    accessToken = accessToken,
                    pageIndex = currentPage
                )

                val data = response.result!!.recipeList
                Log.e("my_scraprecipes_api 실행", data.toString())
                data.forEach{recipes ->
                    responseMapList.add(
                        ScrapRecipe(
                            recipeName = recipes.recipeName,
                            recipeId = recipes.recipeId,
                            thumbnailUrl = recipes.thumbnailUrl,
                            updatedAt = recipes.updatedAt,
                            likes = recipes.likes,
                            createdAt = recipes.createdAt,
                            categoryId = recipes.categoryId,
                            nickname = recipes.nickname,
                            isScrapped = recipes.isScrapped,
                            scraps = recipes.scraps,
                            comments = recipes.comments,
                            isLiked = recipes.isLiked
                        )
                    )
                }
            }
            catch (e: HttpException){
                val errorBody = e.response()?.errorBody()
                val errorCode = errorBody?.getErrorCode()

                if (errorBody != null) {
                    Log.e("my_scraprecipes_api 실패1", "errorBody : ${e.response()?.code()}")
                } else {
                    Log.e("my_scraprecipes_api 실패1", "errorBody : null")
                }

                errorCode?.let{
                    if(errorCode == 4055 || errorCode == 4052 || errorCode ==4054){
                        Log.e("my_scraprecipes_api 실패2-paging 관련", "${errorCode}")
                        scrapRecipesDao.deleteItems()
                        RemoteKeyDao.deleteRemoteKeys()
                        MediatorResult.Success(endOfPaginationReached = true)
                    }
                    else if(errorCode ==4003 || errorCode ==4005 || errorCode ==4008){
                        Log.e("my_scraprecipes_api 실패2-token 관련", "${errorCode}")
                    }
                    else{ }
                }
            }
            catch (e: IOException){
                Log.e("my_scraprecipes_api 실패2", "왜지")
            }
            catch (e: Exception){
                if (e is CancellationException){
                    throw e
                }
                Log.e("my_scraprecipes_api 실패3", "뭐지")
            }



            val endOfPaginationReached = response?.result!!.isLast
            val prevPage = if (currentPage == 1) null else currentPage -1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            paging3Database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    scrapRecipesDao.deleteItems()
                    RemoteKeyDao.deleteRemoteKeys()
                }
                val keys = responseMapList.map {items ->
                    RemoteKeys(
                        id = items.recipeId,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                scrapRecipesDao.addItems(responseMapList)
                RemoteKeyDao.addAllRemoteKeys(keys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached!!)
        }
        catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state : PagingState<Int, ScrapRecipe>
    ) : RemoteKeys? {
        //state.anchorposition은 현재 화면에 보이는 첫 번째 아이템의 포지션
        return state.anchorPosition?.let {position ->
            state.closestItemToPosition(position)?.recipeId?.let{ recipeId ->
                RemoteKeyDao.getRemoteKeys(id = recipeId)
            }
        }
    }

    private suspend fun getRemotekeyForFirstItem(
        state : PagingState<Int, ScrapRecipe>
    ) : RemoteKeys? {
        return state.pages.firstOrNull{ it.data.isNotEmpty() }?.data?.firstOrNull()?.let{item ->
            RemoteKeyDao.getRemoteKeys(id = item.recipeId)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state : PagingState<Int, ScrapRecipe>
    ) : RemoteKeys? {
        return state.pages.lastOrNull{ it.data.isNotEmpty() }?.data?.lastOrNull()?.let{item ->
            RemoteKeyDao.getRemoteKeys(id = item.recipeId)
        }
    }
}