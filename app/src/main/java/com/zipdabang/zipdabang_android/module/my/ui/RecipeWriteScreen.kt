package com.zipdabang.zipdabang_android.module.my.ui

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.my.ui.component.ButtonForIngredient
import com.zipdabang.zipdabang_android.module.my.ui.component.ButtonForStep
import com.zipdabang.zipdabang_android.module.my.ui.component.IngredientAndUnit
import com.zipdabang.zipdabang_android.module.my.ui.component.Step
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteFormEvent
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyViewModel
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.RecipeWriteViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.CustomDialogCameraFile
import com.zipdabang.zipdabang_android.ui.component.CustomDialogRecipeDelete
import com.zipdabang.zipdabang_android.ui.component.CustomDialogType1
import com.zipdabang.zipdabang_android.ui.component.CustomDialogType2
import com.zipdabang.zipdabang_android.ui.component.ImageWithIconAndText
import com.zipdabang.zipdabang_android.ui.component.PrimaryButton
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldForRecipeWriteMultiline
import com.zipdabang.zipdabang_android.ui.component.TextFieldForRecipeWriteSingleline
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// 비트맵을 Uri로 변환하는 함수
fun bitmapToUri(context: Context, bitmap: Bitmap): Uri? {
    val imagesDir =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()
    val imageFileName =
        "IMG_" + SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date()) + ".jpg"
    val imageFile = File(imagesDir, imageFileName)
    val fos: OutputStream
    try {
        fos = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.flush()
        fos.close()
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }

    // 이미지를 미디어 스캔하여 갤러리에 표시
    MediaStore.Images.Media.insertImage(
        context.contentResolver,
        imageFile.absolutePath,
        imageFileName,
        null
    )

    // 파일의 Uri 반환
    return Uri.fromFile(imageFile)
}

@Composable
fun RecipeWriteScreen(
    onClickBack: () -> Unit,
    recipeWriteViewModel: RecipeWriteViewModel = hiltViewModel()
) {
    val stateRecipeWriteForm = recipeWriteViewModel.stateRecipeWriteForm
    val stateRecipeWriteDialog = recipeWriteViewModel.stateRecipeWriteDialog

    LaunchedEffect(key1 =stateRecipeWriteForm.ingredientsNum){

    }
    // textfield
    var textStateIngredient by remember { mutableStateOf("") }
    var textStateUnit by remember { mutableStateOf("") }
    var textStateStep by remember { mutableStateOf("") }

    // 알럿
    val showDialogRecipeDelete = remember { mutableStateOf(false) }
    val showDialogFileSelect = remember { mutableStateOf(false) }
    val showDialogPerimission = remember { mutableStateOf(false) }
    val showDialogSave = remember { mutableStateOf(false) }
    val showDialogUpload = remember { mutableStateOf(false) }
    val showDialogUploadComplete = remember { mutableStateOf(false) }


    // 사진
    var thumbnailUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    // 갤러리->Uri 형식
    val takePhotoFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            thumbnailUri = uri
            Log.e("사진-앨범","${thumbnailUri}")
        }
    // 카메라->Bitmap 형식
    val takePhotoFromCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            if (bitmap != null) {
                /*val baos = ByteArrayOutputStream()
                takenPhoto.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    baos
                )*/
                /*val b : ByteArray = baos.toByteArray()
                val encoded : String = Base64.encodeToString(b, Base64.DEFAULT)*/
                // 비트맵을 Uri로 변환하고 imageUri에 할당
                Log.e("사진-카메라 변환 전","${context}")
                thumbnailUri = bitmapToUri(context, bitmap)
                Log.e("사진-카메라","${thumbnailUri}")
            }
        }




    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = {
                    // 만약 하나라도 차있으면, dialog 띄운 후에 onClickBack()
                    // if(){
                    showDialogRecipeDelete.value = true
                    // } else{
                    //      onClickBack()
                    // }
                },
                centerText = stringResource(id = R.string.my_recipewrite)
            )
        },
        containerColor = Color.White,
        contentColor = Color.White,
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.White)
        ) {
            //썸네일
            Box(
                modifier = Modifier
                    .height(360.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                ImageWithIconAndText(
                    addImageClick = {
                        showDialogFileSelect.value = true
                    },
                    deleteImageClick = {
                        thumbnailUri = null
                    },
                    imageUrl = thumbnailUri,
                    iconImageVector = R.drawable.ic_recipewrite_camera,
                    iconTint = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                    iconModifier = Modifier.size(27.dp, 24.dp),
                    text = stringResource(id = R.string.my_recipewrite_thumbnail_upload),
                    textStyle = ZipdabangandroidTheme.Typography.sixteen_700,
                    textColor = ZipdabangandroidTheme.Colors.Typo.copy(0.5f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 20.dp, 16.dp, 10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // 레시피 제목
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_title),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                    TextFieldForRecipeWriteSingleline(
                        value = stateRecipeWriteForm.title,
                        onValueChanged = { newText, maxLength ->
                            if (newText.length <= maxLength) {
                                recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.TitleChanged(newText))
                            }
                        },
                        maxLength = 20,
                        placeholderValue = stringResource(id = R.string.my_recipewrite_title_hint),
                        imeAction = ImeAction.Next,
                        onClickTrailingicon = {
                            recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.TitleChanged(""))
                        }
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                        text = stateRecipeWriteForm.titleWordCount.toString() + "/20",
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }

                // 소요 시간
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_time),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                    TextFieldForRecipeWriteSingleline(
                        value = stateRecipeWriteForm.time,
                        onValueChanged = { newText, maxLength ->
                            if (newText.length <= maxLength) {
                                recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.TimeChanged(newText))
                            }
                        },
                        maxLength = 20,
                        placeholderValue = stringResource(id = R.string.my_recipewrite_time),
                        imeAction = ImeAction.Next,
                        onClickTrailingicon = {
                            recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.TimeChanged(""))
                        }
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                        text = stateRecipeWriteForm.timeWordCount.toString() + "/20",
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }

                // 레시피 한 줄 소개
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_intro),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                    TextFieldForRecipeWriteMultiline(
                        value = stateRecipeWriteForm.intro,
                        onValueChanged = { newText, maxLength ->
                            if (newText.length <= maxLength) {
                                recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.IntroChanged(newText))
                            }
                        },
                        height = 128.dp,
                        maxLines = 4,
                        maxLength = 100,
                        placeholderValue = stringResource(id = R.string.my_recipewrite_intro_hint),
                        imeAction = ImeAction.None,
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                        text = stateRecipeWriteForm.introWordCount.toString() + "/100",
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }

                // 재료
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_ingredient),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(3f)
                                .padding(4.dp, 0.dp, 0.dp, 0.dp),
                            text = stringResource(id = R.string.my_recipewrite_ingredient),
                            style = ZipdabangandroidTheme.Typography.fourteen_500,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                        Text(
                            modifier = Modifier
                                .weight(4f)
                                .padding(4.dp, 0.dp, 0.dp, 0.dp),
                            text = stringResource(id = R.string.my_recipewrite_unit),
                            style = ZipdabangandroidTheme.Typography.fourteen_500,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                    for (i in  0 until stateRecipeWriteForm.ingredientsNum){
                        IngredientAndUnit(
                            valueIngredient = stateRecipeWriteForm.ingredients[i].ingredientName,
                            onValueChangedIngredient = { newText, maxLength ->
                                if (newText.length <= maxLength) {
                                    textStateIngredient = newText
                                }
                            },
                            placeholderValueIngredient = stringResource(id = R.string.my_recipewrite_milk),
                            maxLengthIngredient = 16,
                            imeActionIngredient = ImeAction.Default,
                            onClickTrailingiconIngredient = {
                                textStateIngredient = ""
                            },
                            onClickCancelIngredient = {

                            },
                            valueUnit = stateRecipeWriteForm.ingredients[i].quantity,
                            onValueChangedUnit = { newText, maxLength ->
                                if (newText.length <= maxLength) {
                                    textStateUnit = newText
                                }
                            },
                            placeholderValueUnit = stringResource(id = R.string.my_recipewrite_hundredmilli),
                            maxLengthUnit = 16,
                            imeActionUnit = ImeAction.Default,
                            onClickTrailingiconUnit = {
                                textStateUnit = ""
                            }
                        )
                    }
                    ButtonForIngredient(
                        borderColor = ZipdabangandroidTheme.Colors.Strawberry,
                        containerColor = Color.White,
                        enabled = true,
                        onClickBtn = {
                            recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnIngredientAdd(ingredientNum = stateRecipeWriteForm.ingredientsNum + 1))
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_recipewrite_warning),
                                contentDescription = "Icon",
                                tint = Color(0xFFB00020),
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(4.dp, 0.dp, 0.dp, 0.dp),
                            )
                            Text(
                                modifier = Modifier.padding(2.dp, 0.dp, 0.dp, 0.dp),
                                text = stringResource(id = R.string.my_recipewrite_warning_ingredient),
                                style = ZipdabangandroidTheme.Typography.twelve_500,
                                color = Color(0xFFB00020)
                            )
                        }
                        Text(
                            modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp),
                            text = stateRecipeWriteForm.ingredientsNum.toString() + "/10",
                            style = ZipdabangandroidTheme.Typography.fourteen_300,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                }

                // 레시피 순서
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_step),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                    for (i in  0 until stateRecipeWriteForm.stepsNum) {
                        Step(
                            stepNum = stateRecipeWriteForm.stepsNum,
                            value = textStateStep,
                            onValueChanged = { newText, maxLength ->
                                if (newText.length <= maxLength) {
                                    textStateStep = newText
                                }
                            },
                            placeholderValue = stringResource(id = R.string.my_recipewrite_step_hint),
                            height = 232.dp,
                            maxLines = 7,
                            maxLength = 200,
                            imeAction = ImeAction.None,
                            onClickImageAddBtn = { },
                            onClickDeleteStep = { },
                            onClickEditStep = { }
                        )
                    }
                    ButtonForStep(
                        borderColor = ZipdabangandroidTheme.Colors.Strawberry,
                        containerColor = Color.White,
                        enabled = true,
                        onClickBtn = {
                            recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.BtnStepAdd(stepNum = stateRecipeWriteForm.stepsNum + 1))
                        }
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                        text = "Step"+ stateRecipeWriteForm.stepsNum + "/Step10",
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }

                // 레시피 Tip
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.my_recipewrite_recipetip),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                    TextFieldForRecipeWriteMultiline(
                        value = stateRecipeWriteForm.recipeTip,
                        onValueChanged = { newText, maxLength ->
                            if (newText.length <= maxLength) {
                                recipeWriteViewModel.onRecipeWriteFormEvent(RecipeWriteFormEvent.RecipeTipChanged(newText))
                            }
                        },
                        height = 224.dp,
                        maxLines = 8,
                        maxLength = 200,
                        placeholderValue = stringResource(id = R.string.my_recipewrite_recipetip_hint),
                        imeAction = ImeAction.None,
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                        text =  stateRecipeWriteForm.recipeTipWordCount.toString() + "/200",
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
            }


            // 하단 버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 12.dp, 16.dp, 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    PrimaryButton(
                        backgroundColor = ZipdabangandroidTheme.Colors.MainBackground,
                        text = stringResource(id = R.string.my_recipewrite_save),
                        onClick = {
                            showDialogSave.value = true
                        },
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    PrimaryButtonWithStatus(
                        isFormFilled = false,
                        text = stringResource(id = R.string.my_recipewrite_writedone),
                        onClick = {},
                    )
                }

            }


            // 알럿
            // 권한허용 알럿
            if (showDialogPerimission.value) {
                CustomDialogType1(
                    title = stringResource(id = R.string.my_dialog_permission),
                    text = stringResource(id = R.string.my_dialog_permission_detail),
                    declineText = stringResource(id = R.string.my_dialog_cancel),
                    acceptText = stringResource(id = R.string.my_dialog_permit),
                    setShowDialog = {
                        showDialogPerimission.value = it
                    },
                    onAcceptClick = {
                        // 카메라 허용받기
                        showDialogPerimission.value = false
                    }
                )
            }
            // 파일선택 알럿
            if (showDialogFileSelect.value) {
                CustomDialogCameraFile(
                    title = stringResource(id = R.string.my_dialog_fileselect),
                    setShowDialog = {
                        showDialogFileSelect.value = it
                    },
                    onCameraClick = {
                        takePhotoFromCameraLauncher.launch()
                        showDialogFileSelect.value = false
                    },
                    onFileClick = {
                        takePhotoFromAlbumLauncher.launch("image/*")
                        showDialogFileSelect.value = false
                    }
                )
            }
            // 임시저장 알럿
            if (showDialogSave.value) {
                CustomDialogType2(
                    title = stringResource(id = R.string.my_save),
                    text = stringResource(id = R.string.my_dialog_save_detail),
                    declineText = stringResource(id = R.string.my_dialog_cancel),
                    acceptText = stringResource(id = R.string.my_save),
                    setShowDialog = {
                        showDialogSave.value = it
                    },
                    onAcceptClick = {
                        // 임시저장 api & navGraph 이동
                        showDialogSave.value = false
                    }
                )
            }
            // 업로드 알럿
            if (showDialogUpload.value) {
                /*CustomDialogSelectCategory(
                    categoryList = ,
                    categoryParagraphList = ,
                    categorySelectedList = ,
                    onSelectClick = {},
                    onCompleteClick = { *//*TODO*//* },
                    setShowDialog = {}
                )*/
            }
            // 작성 중 취소 알럿
            if (showDialogRecipeDelete.value) {
                CustomDialogRecipeDelete(
                    setShowDialog = {
                        showDialogRecipeDelete.value = it
                    },
                    onDeleteClick = {
                        showDialogRecipeDelete.value = false
                        onClickBack()
                    },
                    onTemporalSave = {
                        // 임시저장 api & navGraph 이동
                        showDialogRecipeDelete.value = false
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewRecipeWriteScreen() {
    RecipeWriteScreen(
        onClickBack = {}
    )
}