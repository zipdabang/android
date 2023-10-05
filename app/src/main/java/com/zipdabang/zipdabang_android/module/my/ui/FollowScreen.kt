package com.zipdabang.zipdabang_android.module.my.ui

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val followOrCancelState = viewModel.followOrCancelSuccessState
    val context = LocalContext.current

    LaunchedEffect(key1 =followOrCancelState.value.isSuccess){

    }
    LazyColumn(modifier = Modifier.padding(30.dp)
    ) {
        items(followItem.itemCount){
           FollowItem(
               imageUrl = followItem[it]!!.imageUrl ,
               nickName = followItem[it]!!.nickname ,
               isFollow =  true,
               followOrCancelClick= {
                   viewModel.followOrCancel(followItem[it]!!.id,
                       isToast = {
                           Toast.makeText(context,"팔로우를 취소했습니다.", Toast.LENGTH_LONG).show()
                       })

               },
               userReport = {

               }
           )
        }

    }
}