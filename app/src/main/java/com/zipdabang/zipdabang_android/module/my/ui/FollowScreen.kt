package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
fun FollowScreen(
    viewModel : FriendsListViewModel = hiltViewModel()
) {
    val followItem = viewModel.getFollowItems.collectAsLazyPagingItems()
    LazyColumn(modifier = Modifier.padding(30.dp)
    ) {
        items(followItem.itemCount){
           FollowItem(
               imageUrl = followItem[it]!!.imageUrl ,
               nickName = followItem[it]!!.caption ,
               isFollow =  true
           )
        }

    }
}