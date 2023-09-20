package com.zipdabang.zipdabang_android.ui.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.sign_up.data.remote.BeverageCategory
import com.zipdabang.zipdabang_android.module.sign_up.ui.state.BeverageFormEvent
import com.zipdabang.zipdabang_android.ui.theme.DialogBackground
import com.zipdabang.zipdabang_android.ui.theme.DialogGray
import com.zipdabang.zipdabang_android.ui.theme.DialogPink
import com.zipdabang.zipdabang_android.ui.theme.NavBlack
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

/*@Composable
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
}*/

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
                                text = "카메라",
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
                    text = "작성 중인 레시피 삭제하기",
                    textAlign = TextAlign.Center,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.eighteen_500,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "지금 돌아가면 작성 내용이 모두 삭제됩니다.\n" +
                            "작성 중인 레시피를 삭제하시겠습니까?",
                    textAlign = TextAlign.Start,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                )
                Spacer(modifier = Modifier.height(28.dp))

                PrimaryButtonStrawBerry80(text = "임시저장 하기", onClick = { onTemporalSave() })

                Spacer(modifier = Modifier.height(10.dp))

                PrimaryButtonStrawberry80Outlined(text = "취소", onClick = {
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
                        text = "삭제하기",
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
                        text = "업로드 완료!",
                        style = ZipdabangandroidTheme.Typography.thirtytwo_900_scdream,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "맛있는 레시피를 공유해줘서 고마워요!",
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
                                text = "업로드 레시피 보러가기",
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
                            text = "나중에 보기",
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
                        text = "카테고리 선택",
                        style = ZipdabangandroidTheme.Typography.thirtytwo_900_scdream,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "레시피의 카테고리를 선택해주세요",
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
                            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            for (i in categoryParagraph .. categoryParagraphList[categoryIndex] - 1 + categoryParagraph) {
                                RoundedButton(
                                    imageUrl = categoryList[i].imageUrl,
                                    buttonText = categoryList[i].categoryName,
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
                /*val isEnabled = remember {
                    mutableStateOf(false)
                }
                isEnabled.value = selectedCategory.value < 8*/
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
                        text = "업로드 하기",
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = Color.White
                    )
                }
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
                    text = "현재 준비 중인 서비스에요!",
                    style = ZipdabangandroidTheme.Typography.twentyfour_900_scdream,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "곧 찾아뵙겠습니다. 기대해주세요:)",
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
                    text = "확인",
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
                    text = "현재 준비 중인 서비스에요!",
                    style = ZipdabangandroidTheme.Typography.twentyfour_900_scdream,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "곧 찾아뵙겠습니다. 기대해주세요:)",
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
                    text = "확인",
                    style = ZipdabangandroidTheme.Typography.sixteen_700,
                    color = Color.White
                )

            }
        }
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
