package com.zipdabang.zipdabang_android.module.my.domain.repository

import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.my.data.remote.MyApi
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowMediator
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.Following
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.FollowInfoDB
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.SearchFollowMediator
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.Follower
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingMediator
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search.FollowerInfoDB
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search.SearchFollowerMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagingSearchFollowerRepository @Inject constructor(
    private val myApi: MyApi,
    private val paging3Database: Paging3Database,
    private val tokenDataStore : DataStore<Token>
){
    @OptIn(ExperimentalPagingApi::class)
    fun getFollowerItems(searchText : String): Flow<PagingData<FollowerInfoDB>> {

        val pagingSourceFactory = { paging3Database.searchFollowerDao().getAllItem() }
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = SearchFollowerMediator(
                myApi = myApi,
                paging3Database = paging3Database,
                tokenDataStore = tokenDataStore,
                searchText = searchText
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}