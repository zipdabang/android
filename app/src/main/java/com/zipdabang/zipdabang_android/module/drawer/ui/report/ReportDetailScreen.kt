package com.zipdabang.zipdabang_android.module.drawer.ui.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.detail.recipe.data.RecipeDetailResult
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.detail.ReportDetailResult
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.ReportDetailViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.RectangleImage
import com.zipdabang.zipdabang_android.ui.shimmeringEffect
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun ReportDetailScreen(
    onClickBack : () -> Unit,
    viewModel : ReportDetailViewModel = hiltViewModel()
){

    val scrollState = rememberScrollState()
    val reportDetailState = viewModel.reportDetailState
    var reportDetail : ReportDetailResult? = ReportDetailResult("", emptyList(),"","")
    val isShimmering = remember{
        mutableStateOf(true)
    }

    if(reportDetailState.value.isLoading){
        isShimmering.value = true
    }


    if(reportDetailState.value.isSuccess){
        isShimmering.value = false
        reportDetail = reportDetailState.value.reportDetail
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack()  },
                centerText = "오류문의 및 신고"
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 20.dp
                )
                .background(Color.White)
                .verticalScroll(scrollState)
        ) {

            if(!reportDetailState.value.isError) {
                Text(
                    text = "나의 문의내역",
                    style = ZipdabangandroidTheme.Typography.twentytwo_700,
                    color = ZipdabangandroidTheme.Colors.Typo,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "답변 받을 이메일",
                    style = ZipdabangandroidTheme.Typography.sixteen_700,
                    color = ZipdabangandroidTheme.Colors.Typo
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = reportDetail?.receiveEmail!!,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                        if (isShimmering.value) {
                            Modifier.shimmeringEffect()
                        }else{
                            Modifier
                        }
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "문의 및 신고 내용",
                    style = ZipdabangandroidTheme.Typography.sixteen_700,
                    color = ZipdabangandroidTheme.Colors.Typo
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "제목: " + reportDetail.title!!,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    modifier = Modifier
                            .fillMaxWidth()
                        .then(
                            if (isShimmering.value) {
                                Modifier.shimmeringEffect()
                            }else{
                                Modifier
                            }
                        )
                )
                Text(
                    text = reportDetail.body!!,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (isShimmering.value) {
                                Modifier.shimmeringEffect()
                            }else{
                                Modifier
                            }
                        )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "첨부된 파일",
                    style = ZipdabangandroidTheme.Typography.sixteen_700,
                    color = ZipdabangandroidTheme.Colors.Typo
                )

                LazyRow(
                    modifier = Modifier.height(84.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if(isShimmering.value){
                        items(5){
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(84.dp)
                                    .then(
                                        Modifier.shimmeringEffect()
                                    )
                            )
                        }
                    }
                    else {
                        items(reportDetail.imageList?.size!!) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(84.dp)
                            ) {
                                RectangleImage(
                                    imageUrl = reportDetail.imageList!![it],
                                    contentDescription = "image in Error Report"
                                )

                            }
                        }
                    }
                }
            }
        }


    }

}

@Preview
@Composable
fun ErrorDetailScreen(){
    ReportDetailScreen({})
}