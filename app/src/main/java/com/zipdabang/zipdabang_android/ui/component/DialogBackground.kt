package com.zipdabang.zipdabang_android.ui.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

@Composable
fun GeneralDialogType1(
    title: String,
    text: String,
    declineText: String?,
    acceptText: String,
    setShowDialog: (Boolean) -> Unit,
    onAcceptClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Text(
                text = title,
                color = ZipdabangandroidTheme.Colors.Typo,
                style = ZipdabangandroidTheme.Typography.fourteen_700
            )
        },
        text = {
            Text(
                text = text,
                color = ZipdabangandroidTheme.Colors.Typo,
                style = ZipdabangandroidTheme.Typography.twelve_300
            )

        },
        confirmButton = {
            TextButton(onClick = { onAcceptClick() }) {
                Text(
                    text = acceptText,
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    style = ZipdabangandroidTheme.Typography.sixteen_500
                )

            }
        },
        dismissButton = {
            TextButton(onClick = { setShowDialog(false) }) {
                if (declineText != null) {
                    Text(
                        text = declineText,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style = ZipdabangandroidTheme.Typography.sixteen_500
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
    title: String,
    text: String,
    declineText: String,
    acceptText: String,
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
                    modifier = Modifier.padding(
                        start = 50.dp,
                        end = 50.dp,
                        top = 18.dp,
                        bottom = 8.dp
                    )
                )
                {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style = ZipdabangandroidTheme.Typography.fourteen_700,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = text,
                        textAlign = TextAlign.Center,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style = ZipdabangandroidTheme.Typography.twelve_300,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    TextButton(
                        shape = RectangleShape,
                        onClick = { setShowDialog(false) }) {
                        Text(
                            text = declineText,
                            color = ZipdabangandroidTheme.Colors.Typo,
                            style = ZipdabangandroidTheme.Typography.sixteen_500,
                        )
                    }
                    TextButton(
                        shape = RectangleShape,
                        onClick = { onAcceptClick() }) {
                        Text(
                            text = acceptText,
                            color = ZipdabangandroidTheme.Colors.Strawberry,
                            style = ZipdabangandroidTheme.Typography.sixteen_500,
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
    title: String,
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
                modifier = Modifier.padding(top = 18.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.fourteen_700
                )
                Spacer(modifier = Modifier.height(24.dp))
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
                                modifier = Modifier.size(24.dp, 22.dp),
                                painter = painterResource(id = R.drawable.ic_recipewrite_camera),
                                contentDescription = null,
                                tint = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                            )
                            Text(
                                modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp),
                                text = "카메라",
                                color = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                                style = ZipdabangandroidTheme.Typography.sixteen_500
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
                                modifier = Modifier.size(24.dp, 22.dp),
                                painter = painterResource(id = R.drawable.ic_recipewrite_file),
                                contentDescription = null,
                                tint = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                            )
                            Text(
                                modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp),
                                text = "파일",
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
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style = ZipdabangandroidTheme.Typography.fourteen_700
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "지금 돌아가면 작성 내용이 모두 삭제됩니다.",
                        textAlign = TextAlign.Center,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style = ZipdabangandroidTheme.Typography.twelve_300
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.padding(0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        shape = RectangleShape,
                        modifier = Modifier.padding(0.dp),
                        onClick = { onTemporalSave() }) {
                        Text(
                            text = "임시저장",
                            color = ZipdabangandroidTheme.Colors.Typo,
                            style = ZipdabangandroidTheme.Typography.sixteen_500
                        )

                    }
                    TextButton(
                        shape = RectangleShape,
                        modifier = Modifier.padding(0.dp),
                        onClick = { setShowDialog(false) }) {
                        Text(
                            text = "취소",
                            color = ZipdabangandroidTheme.Colors.Typo,
                            style = ZipdabangandroidTheme.Typography.sixteen_500
                        )
                    }
                    TextButton(
                        shape = RectangleShape,
                        modifier = Modifier.padding(0.dp),
                        onClick = { onDeleteClick() }) {
                        Text(
                            text = "삭제",
                            color = ZipdabangandroidTheme.Colors.Strawberry,
                            style = ZipdabangandroidTheme.Typography.sixteen_500
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
    image: Any,
    setShowDialog: (Boolean) -> Unit,
    onAccept: () -> Unit,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = ZipdabangandroidTheme.Shapes.large,
            color = DialogBackground,
            modifier = Modifier.size(width = 216.dp, height = 467.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*Icon(
                    painter = painterResource(id = R.drawable.ic_alert_close_small),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.End)
                        .clickable {
                            setShowDialog(false)
                        }
                )*/
                Spacer(modifier = Modifier.height(10.dp))

                Box(modifier = Modifier.size(200.dp)) {
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
                        text = "맛있는 레시피를 공유해줘서 고마워요!",
                        textAlign = TextAlign.Center,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style = ZipdabangandroidTheme.Typography.eighteen_500
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
                            color = ZipdabangandroidTheme.Colors.Strawberry,
                            style = ZipdabangandroidTheme.Typography.sixteen_500
                        )

                    }
                    TextButton(
                        shape = RectangleShape,
                        onClick = { setShowDialog(false) }) {
                        Text(
                            text = "나중에 보기",
                            color = ZipdabangandroidTheme.Colors.Typo,
                            style = ZipdabangandroidTheme.Typography.sixteen_500
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
    categoryList: List<String>,
    setShowDialog: (Boolean) -> Unit,
    onSelectClick: (Int) -> Unit,
    onCompleteClick: () -> Unit,
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
                    enabled = isEnabled.value,
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
fun PreviewGeneralDialogType1() {
    val isClickedDialogSave = remember { mutableStateOf(true) }
    val showDialogSave = remember { mutableStateOf(true) }

    if (showDialogSave.value) {
        GeneralDialogType1(
            title = stringResource(id = R.string.my_save),
            text = stringResource(id = R.string.my_dialog_save_detail),
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
fun PreviewCustomDialogType1() {
    val isClickedDialogSave = remember { mutableStateOf(true) }
    val showDialogSave = remember { mutableStateOf(true) }

    if (showDialogSave.value) {
        CustomDialogType1(
            title = stringResource(id = R.string.my_save),
            text = stringResource(id = R.string.my_dialog_save_detail),
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
fun PreviewCustomDialogCameraFile() {
    val showDialogSave = remember { mutableStateOf(true) }
    if(showDialogSave.value){
        CustomDialogCameraFile(
            title = stringResource(id = R.string.my_save),
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
        image = R.drawable.img_profile,
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

    val category = remember { mutableStateOf(8) }
    val categoryList = arrayListOf("커피", "논카페인", "티", "에이드", "스무디", "생과일 음료", "건강 음료", "기타")

    val isEnabled = remember { mutableStateOf(false) }

    if (!showDialog.value) {
        CustomDialogSelectCategory(
            categoryList = categoryList,
            setShowDialog = {
                showDialog.value = it
            },
            onSelectClick =
            {
                category.value = it
            },
            onCompleteClick = {
                if (category.value < 8) {
                    Log.e("category", category.value.toString())
                    isEnabled.value = true
                    showDialog.value = true
                }
            },
            category
        )
    }
}
