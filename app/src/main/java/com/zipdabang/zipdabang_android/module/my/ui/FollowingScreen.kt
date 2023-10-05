package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.module.my.ui.component.FollowItem
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.FriendsListViewModel
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun FollowingScreen(
  viewModel: FriendsListViewModel = hiltViewModel()
){

    val followingItem= viewModel.getFollowingItems.collectAsLazyPagingItems()
    Log.e("followingTest",followingItem.itemCount.toString())
    LazyColumn(modifier = Modifier.padding(30.dp)
        ) {
        items(followingItem.itemCount){
            FollowItem(
                imageUrl = followingItem[it]!!.imageUrl,
                nickName = followingItem[it]!!.nickname,
                isFollow = false,
                isFollowEach = followingItem[it]!!.isFollowing
            )
        }
    }
}