package com.zipdabang.zipdabang_android.module.drawer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.data.remote.noticedto.Notice
import com.zipdabang.zipdabang_android.module.drawer.ui.component.NoticeItem
import com.zipdabang.zipdabang_android.module.drawer.ui.component.ReportItem
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.NoticeViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun NoticeScreen(
    navController: NavController,
    onClickBack : () -> Unit,
    noticeViewModel: NoticeViewModel = hiltViewModel()
){
    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val isShimmering : Boolean
    var noticeList : List<Notice> =remember {
        mutableStateListOf()
    }
    val noticeState = noticeViewModel.noticeState

    if(noticeState.value.isLoading ||noticeState.value.isError) isShimmering = true


    if(noticeViewModel.noticeState.value.isSuccess){
        noticeList = noticeState.value.noticeList
    }

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarSignUp(
                        navigationIcon = R.drawable.ic_topbar_backbtn,
                        onClickNavIcon = { onClickBack() },
                        centerText = stringResource(id = R.string.drawer_notice)
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                content = {
                    LazyColumn(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(
                                top = it.calculateTopPadding(),
                            )
                            .fillMaxSize()

                    ) {
                        if (noticeState.value.isSuccess) {
                            items(noticeList.size) {
                                Column {
                                    NoticeItem(
                                        title = noticeList[it].title,
                                        createdAt = noticeList[it].createdAt,
                                        isNotice = true)
                                    Divider(
                                        modifier = Modifier.fillMaxWidth(),
                                        color = ZipdabangandroidTheme.Colors.Typo.copy(0.2f),
                                    )
                                }
                            }
                        }
                    }
                }
            )
        },
        drawerState = drawerState,
        navController = navController
    )
}