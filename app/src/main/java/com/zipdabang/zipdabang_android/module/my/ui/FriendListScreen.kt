package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.FollowInfoDB
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search.FollowerInfoDB
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.FriendsListViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarDefault
import com.zipdabang.zipdabang_android.ui.component.ColumnPagers
import com.zipdabang.zipdabang_android.ui.component.FriendListSearchBar
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FriendListScreen(
    // viewModel : FriendsListViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    onClickOthers: (Int) -> Unit,
    navController: NavController,
    viewModel: FriendsListViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val searchText = remember{
        mutableStateOf("")
    }
//    val followSearchItem : MutableState<LazyPagingItems<FollowInfoDB>?> = remember {
//        mutableStateOf(null)
//    }
    val followSearchItem : LazyPagingItems<FollowInfoDB> = viewModel.searchFollowings.collectAsLazyPagingItems()
    val followerSearchItem : LazyPagingItems<FollowerInfoDB> = viewModel.searchFollowers.collectAsLazyPagingItems()
    val isSearch = remember{
        mutableStateOf(false)
    }

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                topBar = {
                    AppBarDefault(
                        startIcon = R.drawable.ic_topbar_backbtn,
                        endIcon = R.drawable.ic_topbar_menu,
                        onClickStartIcon = { onClickBack() },
                        onClickEndIcon = { scope.launch { drawerState.open() } },
                        centerText = stringResource(id = R.string.my_friendlist)
                    )
                },
                containerColor = Color.White,
                contentColor = Color.White,
            ) {

                Column(
                    modifier = Modifier
                        .padding(top = it.calculateTopPadding())
                        .fillMaxSize()
                        .background(Color.White)
                ) {

                    Box(
                        modifier = Modifier.padding(16.dp, 10.dp, 16.dp, 0.dp)
                    ) {
                        FriendListSearchBar(
                            iskeyWordEmpty={
                                    it, text ->
                                 if(!it && text != searchText.value){

                                     viewModel.searchFollow(text)
                                     viewModel.searchFollower(text)
                                     searchText.value = text
                                     isSearch.value = true
                                 }
                                 else if(it){
                                     searchText.value = text
                                     isSearch.value = false
                                 }
                            },
                            hintText = stringResource(id = R.string.my_searchbar_person)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))



                    ColumnPagers(
                        tabsList = listOf(
                            TabItem.followList(
                                onClickOthers = onClickOthers,
                                searchFollowItem =  followSearchItem,
                                isSearch = isSearch.value
                            ),
                            TabItem.followingList(
                                onClickOthers = onClickOthers,
                                searchFollowerItem =  followerSearchItem,
                                isSearch = isSearch.value
                            ),

                        ),
                        pagerState = pagerState
                    )
                }

            }

        },
        drawerState = drawerState,
        navController = navController
    )
}

@Preview
@Composable
fun PreviewFriendListScreen() {
    /// FriendListScreen(navController = rememberNavController(), onClickBack = {})
}
