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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.FollowInfoDB
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search.FollowerInfoDB
import com.zipdabang.zipdabang_android.module.my.ui.component.FollowItem
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.FriendsListViewModel
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FollowingScreen(
    onClickOthers : (Int) -> Unit,
    searchFollowerItem : LazyPagingItems<FollowerInfoDB>?,
    isSearch : Boolean,
    viewModel: FriendsListViewModel = hiltViewModel()
) {

    val followerItem = viewModel.followerItems.collectAsLazyPagingItems()

    LaunchedEffect(true) {
        viewModel.getFollowerItems()
    }


    val followOrCancelState = viewModel.followOrCancelSuccessState
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.padding(30.dp)
    ) {
        if (!isSearch) {
            items(followerItem.itemCount) {
                Log.e("following Test", followerItem[it]?.id.toString())
                FollowItem(
                    imageUrl = followerItem[it]!!.imageUrl,
                    nickName = followerItem[it]!!.nickname,
                    isFollow = false,
                    isFollowEach = followerItem[it]!!.isFollowing,
                    followOrCancelClick = {

                        viewModel.followOrCancel(followerItem[it]!!.id,
                            isToast = {
                                Toast.makeText(
                                    context,
                                    "팔로우를 성공했습니다!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            isRefresh = {
                                followerItem.refresh()

                            }
                        )
                        //   viewModel.refresh()


                    },
                    userReport = {
                        TODO()
                    },
                    onClickOthers = {
                        onClickOthers(followerItem[it]!!.id)
                    }

                )
            }
        } else {
            items(searchFollowerItem!!.itemCount)
            {
                // Log.e("following Test",followerItem[it]?.id.toString())
                FollowItem(
                    imageUrl = searchFollowerItem[it]!!.profileUrl,
                    nickName = searchFollowerItem[it]!!.nickname,
                    isFollow = false,
                    isFollowEach = false,
                    followOrCancelClick = {

                        viewModel.followOrCancel(searchFollowerItem[it]!!.memberId,
                            isToast = {
                                Toast.makeText(
                                    context,
                                    "팔로우를 성공했습니다!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            isRefresh = {
                                followerItem.refresh()

                            }
                        )
                        //   viewModel.refresh()


                    },
                    userReport = {
                        TODO()
                    },
                    onClickOthers = {
                        onClickOthers(searchFollowerItem[it]!!.memberId)
                    }

                )


            }
        }
    }
}