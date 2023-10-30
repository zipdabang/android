package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.FollowInfoDB
import com.zipdabang.zipdabang_android.module.my.ui.component.FollowItem
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.FriendsListViewModel
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme


@Composable
fun FollowScreen(
    onClickOthers : (Int) -> Unit,
    searchFollowItem : LazyPagingItems<FollowInfoDB>?,
    isSearch : Boolean,
    viewModel : FriendsListViewModel = hiltViewModel()
) {
    val followItem = viewModel.followItems.collectAsLazyPagingItems()

    LaunchedEffect(true) {
        viewModel.getFollowItems()
    }

    val context = LocalContext.current
    Log.e("followItem in Screen", followItem.itemCount.toString())
    LazyColumn(
        modifier = Modifier.padding(30.dp)
    ) {
        if (!isSearch) {
            items(followItem.itemCount) {
             //   Log.e("followItem",followItem.itemCount.toString())
                FollowItem(
                    imageUrl = followItem[it]!!.imageUrl,
                    nickName = followItem[it]!!.nickname,
                    isFollow = true,
                    followOrCancelClick = {
                        viewModel.followOrCancel(followItem[it]!!.id,
                            isToast = {
                                Toast.makeText(context, "팔로우를 취소했습니다.", Toast.LENGTH_SHORT).show()
                            },
                            isRefresh ={
                                followItem.refresh()
                            }
                        )

                    //    viewModel.refresh()
                    },
                    userReport = {
                        viewModel.userReport(
                            followItem[it]!!.id,
                            isToast = {
                                Toast.makeText(context, "신고를 완료했습니다.", Toast.LENGTH_SHORT).show()

                            },
                            isOwner = {
                                Toast.makeText(context, "본인은 신고할 수 없습니다.", Toast.LENGTH_SHORT).show()

                            }
                            )
                    },
                    onClickOthers = {
                        onClickOthers(followItem[it]!!.id)
                    }

                )
            }
        } else {
            items(searchFollowItem!!.itemCount) {
                FollowItem(
                    imageUrl = searchFollowItem[it]!!.profileUrl,
                    nickName =  searchFollowItem[it]!!.nickname,
                    isFollow = true,
                    followOrCancelClick = {
                        viewModel.followOrCancel(searchFollowItem[it]!!.memberId,
                            isToast = {
                                Toast.makeText(context, "팔로우를 취소했습니다.", Toast.LENGTH_SHORT).show()
                            },
                            isRefresh = {
                                followItem.refresh()
                            }
                        )

                    //    viewModel.refresh()
                    },
                    userReport = {
                        TODO()
                    },
                    onClickOthers = {
                        onClickOthers(searchFollowItem[it]!!.memberId)
                    })
            }

        }
    }
}