package com.zipdabang.zipdabang_android.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.recipes.common.ReportContent
import com.zipdabang.zipdabang_android.ui.theme.DialogBackground
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

//나의 레시피 업로드, 임시저장, 레시피 삭제 -> 버튼 비율 1:1
@Composable
fun CustomDialogType1(
    title: String,
    text: String,
    declineText: String,
    acceptText: String,
    setShowDialog: (Boolean) -> Unit,
    onAcceptClick: () -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            modifier = Modifier
                .size(width = 328.dp, height = 246.dp)
                .fillMaxSize()
                .background(color = DialogBackground, shape = ZipdabangandroidTheme.Shapes.small)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .padding(23.dp)
            )
            {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.eighteen_500,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = text,
                    textAlign = TextAlign.Start,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RectangleShape,
                    onClick = { setShowDialog(false) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0XFFF7F6F6)
                    )
                ) {
                    Text(
                        text = declineText,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                    )
                }
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RectangleShape,
                    onClick = { onAcceptClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ZipdabangandroidTheme.Colors.Strawberry
                    )
                ) {
                    Text(
                        text = acceptText,
                        color = Color.White,
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                    )

                }
            }

        }

    }
}

//버튼 비율 1:2
@Composable
fun CustomDialogType2(
    title: String,
    text: String,
    declineText: String,
    acceptText: String,
    setShowDialog: (Boolean) -> Unit,
    onAcceptClick: () -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            modifier = Modifier
                .size(width = 328.dp, height = 246.dp)
                .fillMaxSize()
                .background(color = DialogBackground, shape = ZipdabangandroidTheme.Shapes.small)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .padding(23.dp)
            )
            {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.eighteen_500,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = text,
                    textAlign = TextAlign.Start,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RectangleShape,
                    onClick = { setShowDialog(false) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0XFFF7F6F6)
                    )
                ) {
                    Text(
                        text = declineText,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                    )
                }
                TextButton(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    shape = RectangleShape,
                    onClick = { onAcceptClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ZipdabangandroidTheme.Colors.Strawberry
                    )
                ) {
                    Text(
                        text = acceptText,
                        color = Color.White,
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                    )

                }
            }

        }

    }
}

//확인 버튼만 있는 알럿
@Composable
fun CustomDialogOnlyConfirm(
    title: String,
    text: String,
    acceptText: String,
    setShowDialog: (Boolean) -> Unit,
    onAcceptClick: () -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            modifier = Modifier
                .size(width = 328.dp, height = 246.dp)
                .fillMaxSize()
                .background(color = DialogBackground, shape = ZipdabangandroidTheme.Shapes.small)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .padding(23.dp)
            )
            {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.eighteen_500,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = text,
                    textAlign = TextAlign.Start,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(56.dp),
            ) {

                TextButton(
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RectangleShape,
                    onClick = { onAcceptClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ZipdabangandroidTheme.Colors.Strawberry
                    )
                ) {
                    Text(
                        text = acceptText,
                        color = Color.White,
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                    )

                }
            }

        }

    }
}

//카메라, 파일 선택
@Composable
fun CustomDialogCameraFile(
    title: String,
    setShowDialog: (Boolean) -> Unit,
    onCameraClick: () -> Unit,
    onFileClick: () -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = ZipdabangandroidTheme.Shapes.small,
            color = DialogBackground,
            modifier = Modifier.size(width = 328.dp, height = 228.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.eighteen_700
                )
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier.padding(start = 35.dp, end = 35.dp, bottom = 40.dp),
                ) {
                    TextButton(
                        shape = RectangleShape,
                        onClick = { onCameraClick() }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(34.dp, 32.dp),
                                painter = painterResource(id = R.drawable.ic_recipewrite_camera),
                                contentDescription = null,
                                tint = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                            )
                            Text(
                                modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp),
                                text = stringResource(id = R.string.dialog_camera),
                                color = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                                style = ZipdabangandroidTheme.Typography.sixteen_500
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(80.dp))
                    TextButton(
                        shape = RectangleShape,
                        onClick = { onFileClick() }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(34.dp, 32.dp),
                                painter = painterResource(id = R.drawable.ic_recipewrite_file),
                                contentDescription = null,
                                tint = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                            )
                            Text(
                                modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp),
                                text = stringResource(id = R.string.dialog_file),
                                color = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                                style = ZipdabangandroidTheme.Typography.sixteen_500
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
    onDeleteClick: () -> Unit,
    onTemporalSave: () -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            modifier = Modifier
                .size(width = 328.dp, height = 332.dp)
                .fillMaxSize()
                .background(color = DialogBackground, shape = ZipdabangandroidTheme.Shapes.small)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(276.dp)
                    .padding(23.dp)
            )
            {
                Text(
                    text = stringResource(id = R.string.dialog_deleterecipe),
                    textAlign = TextAlign.Center,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.eighteen_500,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.dialog_deleterecipe_detail),
                    textAlign = TextAlign.Start,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                )
                Spacer(modifier = Modifier.height(28.dp))

                PrimaryButtonStrawBerry80(text = stringResource(id = R.string.dialog_deleterecipe_save), onClick = { onTemporalSave() })

                Spacer(modifier = Modifier.height(10.dp))

                PrimaryButtonStrawberry80Outlined(text = stringResource(id = R.string.dialog_deleterecipe_cancel), onClick = {
                    setShowDialog(false)
                }
                )


            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                TextButton(
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RectangleShape,
                    onClick = { onDeleteClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ZipdabangandroidTheme.Colors.Strawberry
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.dialog_deleterecipe_delete),
                        color = Color.White,
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                    )

                }
            }

        }

    }
}

//나의 레시피 업로드 완료
@Composable
fun CustomDialogUploadComplete(
    image: Any,
    setShowDialog: (Boolean) -> Unit,
    onAccept: () -> Unit,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = ZipdabangandroidTheme.Shapes.small,
            color = DialogBackground,
            modifier = Modifier
                .size(width = 328.dp, height = 548.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(0.dp)
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = "thumbnail",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                    //.padding(start = 30.dp, end = 30.dp, top = 18.dp, bottom = 8.dp)
                )
                {
                    Text(
                        text = stringResource(id = R.string.dialog_upload),
                        style = ZipdabangandroidTheme.Typography.thirtytwo_900_scdream,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = R.string.dialog_upload_detail),
                        textAlign = TextAlign.Center,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style = ZipdabangandroidTheme.Typography.eighteen_300
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    //modifier = Modifier.padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onAccept() },
                        shape = ZipdabangandroidTheme.Shapes.thin,
                        modifier = Modifier.width(280.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ZipdabangandroidTheme.Colors.Strawberry
                        ),
                        enabled = true
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.dialog_upload_gotouploadrecipe),
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                maxLines = 1,
                                modifier = Modifier,
                                style = ZipdabangandroidTheme.Typography.sixteen_500
                            )
                        }
                    }
                    TextButton(
                        shape = RectangleShape,
                        onClick = { setShowDialog(false) }) {
                        Text(
                            text = stringResource(id = R.string.dialog_upload_seenexttime),
                            color = ZipdabangandroidTheme.Colors.Typo,
                            style = ZipdabangandroidTheme.Typography.fourteen_300
                        )

                    }
                }
            }
        }

    }
}

// 카테고리 선택
@Composable
fun CustomDialogSelectCategory(
    categoryList: List<com.zipdabang.zipdabang_android.module.my.data.remote.BeverageCategory>,
    categoryParagraphList: List<Int>,
    categorySelectedList: List<Boolean>,
    onSelectClick : (Int, Boolean)->Unit,
    onCompleteClick: () -> Unit,
    setShowDialog: (Boolean) -> Unit,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            modifier = Modifier
                .size(width = 320.dp, height = 388.dp)
                .fillMaxSize()
                .background(color = DialogBackground, shape = ZipdabangandroidTheme.Shapes.small),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 40.dp, end = 40.dp, top = 30.dp, bottom = 0.dp)
                )
                {
                    Text(
                        text = stringResource(id = R.string.dialog_selectcategory),
                        style = ZipdabangandroidTheme.Typography.thirtytwo_900_scdream,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.dialog_selectcategory_detail),
                        textAlign = TextAlign.Center,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style = ZipdabangandroidTheme.Typography.eighteen_300
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var categoryIndex = 0
                    var categoryParagraph = 0

                    for(i in 0.. categoryList.size / categoryParagraphList.size + 1) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            for (i in categoryParagraph .. categoryParagraphList[categoryIndex] - 1 + categoryParagraph) {
                                RoundedButton(
                                    imageUrl = categoryList[i].imageUrl,
                                    buttonText = categoryList[i].categoryName,
                                    shimmering = false,
                                    isClicked = categorySelectedList[i],
                                    isClickedChange = { selectedClicked ->
                                        onSelectClick(i, selectedClicked)
                                    }
                                )
                            }
                            categoryParagraph += categoryParagraphList[categoryIndex]
                            categoryIndex ++
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                TextButton(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape,
                    onClick = { onCompleteClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ZipdabangandroidTheme.Colors.Strawberry
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.dialog_selectcategory_upload),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CustomSignupPermission(
    setShowDialog: (Boolean) -> Unit,
    onCheckClick: () -> Unit,
){
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            modifier = Modifier
                .size(width = 328.dp, height = 472.dp)
                .fillMaxHeight()
                .background(color = DialogBackground, shape = ZipdabangandroidTheme.Shapes.small)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp, 30.dp, 20.dp, 0.dp)
            ){
                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    MainAndSubTitle(
                        mainValue = stringResource(id = R.string.dialog_permission),
                        mainTextStyle = ZipdabangandroidTheme.Typography.twentytwo_700,
                        mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                        subValue = stringResource(id = R.string.dialog_permission_detail),
                        subTextStyle = ZipdabangandroidTheme.Typography.sixteen_300,
                        subTextColor = ZipdabangandroidTheme.Colors.Typo
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                ){
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .aspectRatio(1f)
                            .background(
                                color = ZipdabangandroidTheme.Colors.Strawberry,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            modifier = Modifier.size(32.dp, 32.dp),
                            painter = painterResource(id = R.drawable.ic_signup_camerapermission),
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.4f))
                    Box(
                        modifier = Modifier.weight(7f),
                    ){
                        MainAndSubTitle(
                            mainValue = stringResource(id = R.string.dialog_permission_camera),
                            mainTextStyle = ZipdabangandroidTheme.Typography.sixteen_700,
                            mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                            subValue =  stringResource(id = R.string.dialog_permission_cameradetail),
                            subTextStyle = ZipdabangandroidTheme.Typography.twelve_300,
                            subTextColor = ZipdabangandroidTheme.Colors.Typo,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                ){
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .aspectRatio(1f)
                            .background(
                                color = ZipdabangandroidTheme.Colors.Strawberry,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            modifier = Modifier.size(32.dp, 32.dp),
                            painter = painterResource(id = R.drawable.ic_signup_gallerypermission),
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.4f))
                    Box(
                        modifier = Modifier.weight(7f),
                    ){
                        MainAndSubTitle(
                            mainValue = stringResource(id = R.string.dialog_permission_gallery),
                            mainTextStyle = ZipdabangandroidTheme.Typography.sixteen_700,
                            mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                            subValue = stringResource(id = R.string.dialog_permission_gallerydetail),
                            subTextStyle = ZipdabangandroidTheme.Typography.twelve_300,
                            subTextColor = ZipdabangandroidTheme.Colors.Typo,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                ){
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .aspectRatio(1f)
                            .background(
                                color = ZipdabangandroidTheme.Colors.Strawberry,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            modifier = Modifier.size(32.dp, 32.dp),
                            painter = painterResource(id = R.drawable.ic_signup_addresspermission),
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.4f))
                    Box(
                        modifier = Modifier.weight(7f),
                    ){
                        MainAndSubTitle(
                            mainValue = stringResource(id = R.string.dialog_permission_address),
                            mainTextStyle = ZipdabangandroidTheme.Typography.sixteen_700,
                            mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                            subValue = stringResource(id = R.string.dialog_permission_addressdetail),
                            subTextStyle = ZipdabangandroidTheme.Typography.twelve_300,
                            subTextColor = ZipdabangandroidTheme.Colors.Typo,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                ){
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .aspectRatio(1f)
                            .background(
                                color = ZipdabangandroidTheme.Colors.Strawberry,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            modifier = Modifier.size(32.dp, 32.dp),
                            painter = painterResource(id = R.drawable.ic_signup_alarmpermission),
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.4f))
                    Box(
                        modifier = Modifier.weight(7f),
                    ){
                        MainAndSubTitle(
                            mainValue = stringResource(id = R.string.dialog_permission_alarm),
                            mainTextStyle = ZipdabangandroidTheme.Typography.sixteen_700,
                            mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                            subValue = stringResource(id = R.string.dialog_permission_alarmdetail),
                            subTextStyle = ZipdabangandroidTheme.Typography.twelve_300,
                            subTextColor = ZipdabangandroidTheme.Colors.Typo,
                        )
                    }
                }
            }
            TextButton(
                enabled = true,
                colors = ButtonDefaults.buttonColors(containerColor = ZipdabangandroidTheme.Colors.Strawberry),
                shape = ZipdabangandroidTheme.Shapes.smallRoundedBottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter),
                contentPadding = PaddingValues(0.dp),
                onClick = { onCheckClick() }
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_permission_check),
                    style = ZipdabangandroidTheme.Typography.sixteen_700,
                    color = Color.White
                )
            }
        }
    }
}

// 마켓 준비중
@Composable
fun CustomMarketReady(
    setShowDialog: (Boolean) -> Unit,
    onCheckClick: () -> Unit,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            modifier = Modifier
                .size(width = 328.dp, height = 450.dp)
                .fillMaxHeight()
                .background(color = DialogBackground, shape = ZipdabangandroidTheme.Shapes.small)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp)
            ) {
                Text(
                    text =  stringResource(id = R.string.dialog_ready),
                    style = ZipdabangandroidTheme.Typography.twentyfour_900_scdream,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text =  stringResource(id = R.string.dialog_ready_detail),
                    style = ZipdabangandroidTheme.Typography.eighteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo
                )

                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier.size(width = 216.dp, height = 182.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.market_ready),
                        contentDescription = "market_ready", modifier = Modifier.fillMaxSize()
                    )
                }
            }
            TextButton(
                enabled = true,
                colors = ButtonDefaults.buttonColors(containerColor = ZipdabangandroidTheme.Colors.Strawberry),
                shape = ZipdabangandroidTheme.Shapes.smallRoundedBottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter),
                contentPadding = PaddingValues(0.dp),
                onClick = { onCheckClick() }
            ) {
                Text(
                    text =  stringResource(id = R.string.dialog_ready_check),
                    style = ZipdabangandroidTheme.Typography.sixteen_700,
                    color = Color.White
                )

            }
        }
    }
}

// 장바구니 준비중
@Composable
fun CustomBasketReady(
    setShowDialog: (Boolean) -> Unit,
    onCheckClick: () -> Unit,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            modifier = Modifier
                .size(width = 328.dp, height = 450.dp)
                .fillMaxHeight()
                .background(color = DialogBackground, shape = ZipdabangandroidTheme.Shapes.small)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_ready),
                    style = ZipdabangandroidTheme.Typography.twentyfour_900_scdream,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.dialog_ready_detail),
                    style = ZipdabangandroidTheme.Typography.eighteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo
                )

                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier.size(width = 216.dp, height = 216.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.basket_ready),
                        contentDescription = "market_ready", modifier = Modifier.fillMaxSize()
                    )
                }
            }
            TextButton(
                enabled = true,
                colors = ButtonDefaults.buttonColors(containerColor = ZipdabangandroidTheme.Colors.Strawberry),
                shape = ZipdabangandroidTheme.Shapes.smallRoundedBottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomCenter),
                contentPadding = PaddingValues(0.dp),
                onClick = { onCheckClick() }
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_ready_check),
                    style = ZipdabangandroidTheme.Typography.sixteen_700,
                    color = Color.White
                )

            }
        }
    }
}

@Composable
fun RecipeReportDialog(
    title: String,
    recipeId: Int,
    reportId: Int,
    declineText: String,
    acceptText: String,
    reportContentList: List<ReportContent>,
    setShowDialog: (Boolean) -> Unit,
    onReportClick: (Int) -> Unit,
    onReportContentChange: (Int) -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .background(color = DialogBackground, shape = ZipdabangandroidTheme.Shapes.small)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.eighteen_700,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
                )

                Spacer(modifier = Modifier.width(20.dp))

                RadioGroupReportVertical(
                    optionList = reportContentList,
                    onOptionChange = onReportContentChange
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                ) {
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RectangleShape,
                        onClick = { setShowDialog(false) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0XFFF7F6F6)
                        )
                    ) {
                        Text(
                            text = declineText,
                            color = ZipdabangandroidTheme.Colors.Typo,
                            style = ZipdabangandroidTheme.Typography.sixteen_500,
                        )
                    }
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RectangleShape,
                        onClick = {
                            onReportClick(reportId)
                            setShowDialog(false)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ZipdabangandroidTheme.Colors.Strawberry
                        )
                    ) {
                        Text(
                            text = acceptText,
                            color = Color.White,
                            style = ZipdabangandroidTheme.Typography.sixteen_500,
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun CommentReportDialog(
    title: String,
    recipeId: Int,
    commentId: Int,
    reportId: Int,
    declineText: String,
    acceptText: String,
    reportContentList: List<ReportContent>,
    setShowDialog: (Boolean) -> Unit,
    onReportClick: (Int, Int) -> Unit,
    onReportContentChange: (Int) -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .background(color = DialogBackground, shape = ZipdabangandroidTheme.Shapes.small)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.eighteen_700,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
                )

                Spacer(modifier = Modifier.width(20.dp))

                RadioGroupReportVertical(
                    optionList = reportContentList,
                    onOptionChange = onReportContentChange
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                ) {
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RectangleShape,
                        onClick = { setShowDialog(false) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0XFFF7F6F6)
                        )
                    ) {
                        Text(
                            text = declineText,
                            color = ZipdabangandroidTheme.Colors.Typo,
                            style = ZipdabangandroidTheme.Typography.sixteen_500,
                        )
                    }
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RectangleShape,
                        onClick = {
                            onReportClick(commentId, reportId)
                            setShowDialog(false)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ZipdabangandroidTheme.Colors.Strawberry
                        )
                    ) {
                        Text(
                            text = acceptText,
                            color = Color.White,
                            style = ZipdabangandroidTheme.Typography.sixteen_500,
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun UserBlockDialog(
    title: String,
    text: String,
    declineText: String,
    acceptText: String,
    setShowDialog: (Boolean) -> Unit,
    onAcceptClick: () -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .background(color = DialogBackground, shape = ZipdabangandroidTheme.Shapes.small)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.eighteen_700,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = text,
                    textAlign = TextAlign.Start,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.sixteen_500,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
                )


                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                ) {
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RectangleShape,
                        onClick = { setShowDialog(false) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0XFFF7F6F6)
                        )
                    ) {
                        Text(
                            text = declineText,
                            color = ZipdabangandroidTheme.Colors.Typo,
                            style = ZipdabangandroidTheme.Typography.sixteen_500,
                        )
                    }
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RectangleShape,
                        onClick = {
                            onAcceptClick()
                            setShowDialog(false)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ZipdabangandroidTheme.Colors.Strawberry
                        )
                    ) {
                        Text(
                            text = acceptText,
                            color = Color.White,
                            style = ZipdabangandroidTheme.Typography.sixteen_500,
                        )

                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewCustomSignupPermission() {
    val showDialog = remember { mutableStateOf(true) }

    if(showDialog.value){
        CustomSignupPermission(
            setShowDialog = {
                showDialog.value = it
            },
            onCheckClick = { /*TODO*/ },
        )
    }
}

@Preview
@Composable
fun PreviewCustomBasketReady() {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        CustomBasketReady(setShowDialog = {
            showDialog.value = it
        }) {
            showDialog.value = false
        }
    }
}

@Preview
@Composable
fun PreviewCustomMarketReady() {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        CustomMarketReady(setShowDialog = {
            showDialog.value = it
        }) {
            showDialog.value = false
        }
    }
}

@Preview
@Composable
fun PreviewCustomDialogType1() {
    val showDialogSave = remember { mutableStateOf(true) }

    if (showDialogSave.value) {
        CustomDialogType1(
            title = stringResource(id = R.string.my_save),
            text = "회원님이 집다방에 사진과 동영상을 공유하고 공유할 사진 및 동영상을 추천하는 등의 목적으로 집다방이 회원님의 카메라롤에 대한 액세스 권한을 요청합니다.",
            declineText = stringResource(id = R.string.my_dialog_cancel),
            acceptText = stringResource(id = R.string.my_save),
            setShowDialog = {
                showDialogSave.value = it
            },
            onAcceptClick = {
                showDialogSave.value = false
            }
        )
    }
}

@Preview
@Composable
fun PreviewCustomDialogType2() {
    val showDialogSave = remember { mutableStateOf(true) }

    if (showDialogSave.value) {
        CustomDialogType2(
            title = stringResource(id = R.string.my_save),
            text = "회원님이 집다방에 사진과 동영상을 공유하고 공유할 사진 및 동영상을 추천하는 등의 목적으로 집다방이 회원님의 카메라롤에 대한 액세스 권한을 요청합니다.",
            declineText = stringResource(id = R.string.my_dialog_cancel),
            acceptText = stringResource(id = R.string.my_save),
            setShowDialog = {
                showDialogSave.value = it
            },
            onAcceptClick = {
                showDialogSave.value = false
            }
        )
    }
}

@Preview
@Composable
fun PreviewCustomDialogOnlyConfirm() {
    val showDialogSave = remember { mutableStateOf(true) }

    if (showDialogSave.value) {
        CustomDialogOnlyConfirm(
            title = "집다방 탈퇴 완료",
            text = "지금까지 집다방을 이용해주셔서 감사합니다. 다음에도 좋은 인연으로 만날 수 있길 바랍니다. 건강하고 행복하세요!",
            acceptText = "확인",
            setShowDialog = {
                showDialogSave.value = it
            },
            onAcceptClick = {
                showDialogSave.value = false
            }
        )
    }
}

@Preview
@Composable
fun PreviewCustomDialogCameraFile() {
    val showDialogSave = remember { mutableStateOf(true) }
    if (showDialogSave.value) {
        CustomDialogCameraFile(
            title = stringResource(id = R.string.my_dialog_fileselect),
            setShowDialog = {
                showDialogSave.value = it
            },
            onCameraClick = { },
            onFileClick = { }
        )
    }
}

@Preview
@Composable
fun PreviewCustomDialogRecipeDelete() {
    val showDialogSave = remember { mutableStateOf(false) }

    CustomDialogRecipeDelete(
        setShowDialog = {
            showDialogSave.value = it
        },
        onDeleteClick = { },
        onTemporalSave = { }
    )
}

@Preview
@Composable
fun PreviewCustomDialogUploadComplete() {
    val showDialogSave = remember { mutableStateOf(false) }

    CustomDialogUploadComplete(
        image = R.drawable.img_recipewrite_thumbnail,
        setShowDialog = {
            showDialogSave.value = it
        },
        onAccept = { }
    )
}

@Preview
@Composable
fun PreviewCustomDialogSelectCategory() {
    val showDialog = remember { mutableStateOf(false) }

    val categoryList = listOf(
        com.zipdabang.zipdabang_android.module.my.data.remote.BeverageCategory(
            categoryName = "커피",
            id = 1,
            imageUrl = ""
        ),
        com.zipdabang.zipdabang_android.module.my.data.remote.BeverageCategory(
            categoryName = "논카페인",
            id = 2,
            imageUrl = ""
        ),
        com.zipdabang.zipdabang_android.module.my.data.remote.BeverageCategory(
            categoryName = "차",
            id = 3,
            imageUrl = ""
        ),
        com.zipdabang.zipdabang_android.module.my.data.remote.BeverageCategory(
            categoryName = "에이드",
            id = 4,
            imageUrl = ""
        ),
        com.zipdabang.zipdabang_android.module.my.data.remote.BeverageCategory(
            categoryName = "스무디",
            id = 5,
            imageUrl = ""
        ),
        com.zipdabang.zipdabang_android.module.my.data.remote.BeverageCategory(
            categoryName = "과일음료",
            id = 6,
            imageUrl = ""
        ),
        com.zipdabang.zipdabang_android.module.my.data.remote.BeverageCategory(
            categoryName = "건강음료",
            id = 7,
            imageUrl = ""
        ),
        com.zipdabang.zipdabang_android.module.my.data.remote.BeverageCategory(
            categoryName = "기타",
            id = 8,
            imageUrl = ""
        ),
    )
    val categorySelectedList = remember { mutableStateListOf(false, false, false, false, false, false, false, false) }

    val isEnabled = remember { mutableStateOf(false) }

    if (!showDialog.value) {
        CustomDialogSelectCategory(
            categoryParagraphList = listOf(3, 2, 2, 1),
            categorySelectedList = categorySelectedList,
            categoryList = categoryList,
            onSelectClick= { index, clicked ->
                categorySelectedList[index] = clicked
                Log.e("categorySelectedList", "${categorySelectedList[0]} ${categorySelectedList[1]} ${categorySelectedList[2]} " +
                        "${categorySelectedList[3]} ${categorySelectedList[4]} ${categorySelectedList[5]} ${categorySelectedList[6]} " +
                        "${categorySelectedList[7]}" )
            },
            onCompleteClick = {

            },
            setShowDialog = {
                showDialog.value = it
            },
        )
    }
}

@Preview
@Composable
fun CommentReportDialogPreview() {
    CommentReportDialog(
        title = "댓글 신고하기",
        recipeId = 1,
        commentId = 1,
        reportId = 1,
        declineText = "취소",
        acceptText = "신고하기",
        reportContentList = ReportContent.contents,
        setShowDialog = { boolean -> },
        onReportClick = { repor, re ->},
        onReportContentChange = { re -> }
    )
}

@Preview
@Composable
fun BlockDialogPreview() {
    UserBlockDialog(
        title = "댓글 신고하기",
        declineText = "취소",
        acceptText = "신고하기",
        setShowDialog = { boolean -> },
        text = "해당 게시글을 차단하게되면,\n" +
                "더이상 회원님에게 보이지 않게됩니다.\n" +
                "게시글을 차단하시겠습니까?",
        onAcceptClick = {}
    )
}