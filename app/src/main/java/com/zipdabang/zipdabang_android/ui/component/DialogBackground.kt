package com.zipdabang.zipdabang_android.ui.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.DialogBackground
import com.zipdabang.zipdabang_android.ui.theme.DialogGray
import com.zipdabang.zipdabang_android.ui.theme.DialogPink
import com.zipdabang.zipdabang_android.ui.theme.NavBlack
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GeneralDialogType1(
   title : String,
   text : String,
   declineText : String?,
   acceptText : String,
   setShowDialog: (Boolean) -> Unit ,
   onAcceptClick : ()->Unit,
   ){

        AlertDialog(
            onDismissRequest = { setShowDialog(false) },
            title = {
                Text(
                         text = title,
                         color =  NavBlack,
                         fontSize = 14.sp,
                         fontWeight = FontWeight(700)
                        )

                    },
            text = {
                Text(
                    text = text,
                    color =  NavBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400)
                )

                   },
            confirmButton = {
                TextButton(onClick = { onAcceptClick() }) {
                    Text(
                        text = acceptText,
                        color =  DialogPink,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500)
                    )

                }
            },
            dismissButton = {
                TextButton(onClick = { setShowDialog(false)}) {
                    if (declineText != null) {
                        Text(
                            text = declineText,
                            color =  NavBlack,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500)
                        )
                    }
                }
            },
            shape = ZipdabangandroidTheme.Shapes.small,
            containerColor = DialogBackground
        )
    }

//나의 레시피 업로드, 임시저장, 레시피 삭제
@Composable
fun CustomDialogType1(
    title : String,
    text : String,
    declineText : String,
    acceptText : String,
    setShowDialog: (Boolean) -> Unit,
    onAcceptClick: () -> Unit
) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = ZipdabangandroidTheme.Shapes.small,
            color = DialogBackground,
            modifier = Modifier.size(width = 216.dp, height = 167.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 50.dp, end = 50.dp, top = 18.dp, bottom = 8.dp)
                )
                {
                    Text(
                        text = title,
                        color = NavBlack,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = text,
                        textAlign = TextAlign.Center,
                        color = NavBlack,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400)
                    )

                }


                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                ) {
                    TextButton(
                        shape = RectangleShape,
                        onClick = { setShowDialog(false) }) {
                        Text(
                            text = declineText,
                            color = NavBlack,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500)
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))

                    TextButton(
                        shape = RectangleShape,
                        onClick = { onAcceptClick() }) {
                        Text(
                            text = acceptText,
                            color = DialogPink,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500)
                        )

                    }
                }
            }
        }

    }
}
//카메라, 파일 선택
@Composable
fun CustomDialogCameraFile(
    title : String,
    setShowDialog: (Boolean) -> Unit,
    onCameraClick: () -> Unit,
    onFileClick: () -> Unit
) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = ZipdabangandroidTheme.Shapes.small,
            color = DialogBackground,
            modifier = Modifier.size(width = 216.dp, height = 191.dp)
        ) {

            Column(
                modifier = Modifier.padding(top = 18.dp ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                        text = title,
                        color = NavBlack,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700)
                    )

                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier.padding(start = 35.dp,end =35.dp,bottom= 60.dp),
                ) {
                    TextButton(
                        shape = RectangleShape,
                        onClick = { onCameraClick() }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.ic_alert_camera_small),
                                contentDescription = null
                            )
                            Text(
                                text = "카메라",
                                color = DialogGray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight(500)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(20.dp))

                    TextButton(
                        shape = RectangleShape,
                        onClick = { onFileClick() }) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.ic_alert_file_small),
                                contentDescription = null
                            )
                            Text(
                                text = "파일",
                                color = DialogGray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight(500)
                            )
                        }

                    }
                }
            }
        }

    }
}


//레시지 삭제
@Composable
fun CustomDialogRecipeDelete(
    setShowDialog: (Boolean) -> Unit,
    onDeleteClick:() -> Unit,
    onTemporalSave : () -> Unit
) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = ZipdabangandroidTheme.Shapes.small,
            color = DialogBackground,
            modifier = Modifier.size(width = 216.dp, height = 255.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 40.dp, end = 40.dp, top = 18.dp, bottom = 8.dp)
                )
                {
                    Text(
                        text = "레시피 삭제",
                        color = NavBlack,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "지금 돌아가면 작성 내용이 모두 삭제됩니다.",
                        textAlign = TextAlign.Center,
                        color = NavBlack,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400)
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(
                        shape = RectangleShape,
                        onClick = { onDeleteClick() }) {
                        Text(
                            text = "삭제",
                            color = NavBlack,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500)
                        )
                    }
                    TextButton(
                        shape = RectangleShape,
                        onClick = { setShowDialog(false) }) {
                        Text(
                            text = "취소",
                            color = NavBlack,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500)
                        )

                    }
                    TextButton(
                        shape = RectangleShape,
                        onClick = { onTemporalSave() }) {
                        Text(
                            text = "임시저장",
                            color = NavBlack,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500)
                        )

                    }
                }
            }
        }

    }
}

//나의 레시피 업로드 완료
@Composable
fun CustomDialogUploadComplete(
    image : String,
    setShowDialog: (Boolean) -> Unit,
    onAccept:() -> Unit,
) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = ZipdabangandroidTheme.Shapes.small,
            color = DialogBackground,
            modifier = Modifier.size(width = 216.dp, height = 467.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_alert_close_small),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.End)
                        .clickable {
                            setShowDialog(false)
                        }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Box(modifier = Modifier.size(200.dp)){
                    RectangleWithRadiusImage(
                        imageUrl = image,
                        contentDescription = "image in dialog"
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 40.dp, end = 40.dp, top = 18.dp, bottom = 8.dp)
                )
                {
                    Text(
                        text = "업로드 완료!",
                        color = NavBlack,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "멋진 홈카페 레시피를 공유해줘서 고마워요!",
                        textAlign = TextAlign.Center,
                        color = NavBlack,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400)
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButton(
                        shape = ZipdabangandroidTheme.Shapes.small,
                        colors = ButtonDefaults.buttonColors(DialogPink),
                        onClick = { onAccept() }) {
                        Text(
                            text = "업로드 레시피 보러가기",
                            color = NavBlack,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500)
                        )

                    }
                    TextButton(
                        shape = RectangleShape,
                        onClick = { setShowDialog(false) }) {
                        Text(
                            text = "나중에 보기",
                            color = NavBlack,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(300)
                        )

                    }
                }
            }
        }

    }
}

//레시지 삭제
@Composable
fun CustomDialogSelectCategory(
    categoryList: ArrayList<String>,
    setShowDialog: (Boolean) -> Unit,
    onSelectClick: (Int) -> Unit,
    onCompleteClick:  () -> Unit,
    selectedCategory: MutableState<Int>,
) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = ZipdabangandroidTheme.Shapes.small,
            color = DialogBackground,
            modifier = Modifier.size(width = 216.dp, height = 343.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 40.dp, end = 40.dp, top = 18.dp, bottom = 8.dp)
                )
                {
                    Text(
                        text = "업로드",
                        color = NavBlack,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "레시피의 카테고리를 선택해주세요",
                        textAlign = TextAlign.Center,
                        color = NavBlack,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400)
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))

                NonlazyGrid(
                    columns = 2,
                    itemCount = categoryList.size,
                    modifier = Modifier
                        .padding(start = 9.dp, end = 9.dp, top = 5.dp, bottom = 5.dp)
                ) {
                    if (selectedCategory.value == it) {
                        TextButton(
                            onClick = {
                                onSelectClick(8)
                            },
                            modifier = Modifier.size(width = 96.dp, height = 36.dp),
                            shape = ZipdabangandroidTheme.Shapes.small,
                            colors = ButtonDefaults.buttonColors(ZipdabangandroidTheme.Colors.Strawberry)
                        )
                        {
                            Text(
                                text = categoryList[it],
                                color = NavBlack,
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                fontWeight = FontWeight(500)
                            )
                        }

                    } else {
                        TextButton(
                            onClick = {
                                onSelectClick(it)
                            },
                            modifier = Modifier.size(width = 96.dp, height = 36.dp),
                            shape = ZipdabangandroidTheme.Shapes.small,
                            colors = ButtonDefaults.buttonColors(DialogBackground),
                            border = BorderStroke(1.dp, DialogGray)
                        )
                        {
                            Text(
                                text = categoryList[it],
                                color = NavBlack,
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                fontWeight = FontWeight(500)
                            )
                        }


                    }
                }

                val isEnabled = remember {
                    mutableStateOf(false)
                }
                isEnabled.value = selectedCategory.value < 8

                TextButton(
                    enabled= isEnabled.value,
                    shape = RectangleShape,
                    onClick = { onCompleteClick() }
                ) {
                    Text(
                        text = "선택 완료",
                        color = NavBlack,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700)
                    )

                }
            }

        }

    }
}


@Preview
@Composable
fun DialogBackgroundPreview(){

    val showDialog =  remember { mutableStateOf(false) }

   if(showDialog.value) {
       GeneralDialogType1(
           title = "작성 중인 글 삭제",
           text = "다른 페이지 이동으로 작성 중인 게시글은 삭제됩니다. 다른페이지로 이동하시겠습니끼",
           declineText = "아니오",
           acceptText = "네",
           setShowDialog = {
               showDialog.value = it
           },
           {}
       )
   }
    if(showDialog.value) {
        CustomDialogType1(
            title = "업로드",
            text = "작성 완료한 레시피를 임시저장 하시겠습니까",
            declineText = "취소",
            acceptText = "임시저장",
            setShowDialog = {
                showDialog.value = it
            },
            {}
        )
    }
    if(showDialog.value) {
     CustomDialogCameraFile(
         title = "파일 선택",
         setShowDialog= {
             showDialog.value = it
         },
         onCameraClick = { /*TODO*/ }) {
     }
    }
    if(showDialog.value) {
        CustomDialogRecipeDelete(
            setShowDialog= {
                showDialog.value = it
            },
            onDeleteClick = {},
            onTemporalSave ={} )
    }
    if(showDialog.value) {
        CustomDialogUploadComplete(
             image ="https://pds.joongang.co.kr/svcimg/newsletter/content/202203/14/3450436a-5c23-4cc9-b8a4-2ab7c2ca8b76.jpg",
            setShowDialog = {
                showDialog.value = it
            },
            onAccept= {}
        )
    }

    val category = remember { mutableStateOf(8) }

    val categoryList = arrayListOf("커피","논카페인","티","에이드","스무디","생과일 음료","건강 음료","기타")

    val isEnabled = remember { mutableStateOf(false) }
    if(!showDialog.value){
        CustomDialogSelectCategory(
            categoryList = categoryList,
            setShowDialog = {
                showDialog.value=it
            },
            onSelectClick =
          {
            category.value = it
          },
            onCompleteClick = {
                if(category.value < 8) {
                    Log.e("category", category.value.toString())
                    isEnabled.value= true
                    showDialog.value= true
                }
            },
            category
        )
    }
    if(isEnabled.value){
        CustomDialogType1(
            title = "업로드",
            text = "작성 완료한 레시피를 업로드 하시겠습니까",
            declineText = "취소",
            acceptText = "업로드",
            setShowDialog = {
                showDialog.value = it
            },
            {}
        )
    }








}
