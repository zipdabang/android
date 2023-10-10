package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FollowingScreen(
    onClickOthers : (Int) -> Unit,
    viewModel: FriendsListViewModel = hiltViewModel()
){

    val followingItem= viewModel.getFollowingItems.collectAsLazyPagingItems()

    val followOrCancelState = viewModel.followOrCancelSuccessState
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.padding(30.dp)
        ) {
        items(followingItem.itemCount){
            Log.e("following Test",followingItem[it]?.id.toString())
            FollowItem(
                imageUrl = followingItem[it]!!.imageUrl,
                nickName = followingItem[it]!!.nickname,
                isFollow = false,
                isFollowEach = followingItem[it]!!.isFollowing,
                followOrCancelClick= {

                    viewModel.followOrCancel(followingItem[it]!!.id,
                        isToast = {
                            Toast.makeText(
                                context,
                                "팔로우를 성공했습니다!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                    followingItem.refresh()
                    viewModel.refresh()



                },
                userReport = {
                             TODO()
                },
                onClickOthers = {
                    onClickOthers(followingItem[it]!!.id)
                }

            )
        }
    }
}