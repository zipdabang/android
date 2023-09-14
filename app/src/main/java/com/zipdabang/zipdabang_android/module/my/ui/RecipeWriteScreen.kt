package com.zipdabang.zipdabang_android.module.my.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.my.ui.component.ButtonForIngredient
import com.zipdabang.zipdabang_android.module.my.ui.component.IngredientAndUnit
import com.zipdabang.zipdabang_android.module.my.ui.component.Step
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.CustomDialogType1
import com.zipdabang.zipdabang_android.ui.component.ImageWithIconAndText
import com.zipdabang.zipdabang_android.ui.component.PrimaryButton
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldForRecipeWriteMultiline
import com.zipdabang.zipdabang_android.ui.component.TextFieldForRecipeWriteSingleline
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeWriteScreen(
    onClickBack: () -> Unit
) {
    // textfield
    var textState by remember { mutableStateOf("") }
    var textStateTime by remember { mutableStateOf("") }
    var textStateIntro by remember { mutableStateOf("") }
    var textStateIngredient by remember { mutableStateOf("") }
    var textStateUnit by remember { mutableStateOf("") }
    var textStateStep by remember { mutableStateOf("") }
    var textStateTip by remember { mutableStateOf("") }

    // 알럿
    val isClickedDialogFileSelect = remember { mutableStateOf(false) }
    val showDialogFileSelect = remember { mutableStateOf(false) }
    val isClickedDialogPerimission = remember { mutableStateOf(false) }
    val showDialogPerimission = remember { mutableStateOf(false) }
    val isClickedDialogSave = remember { mutableStateOf(false) }
    val showDialogSave = remember { mutableStateOf(false) }
    val isClickedDialogUpload = remember { mutableStateOf(false) }
    val showDialogUpload = remember { mutableStateOf(false) }
    val isClickedDialogUploadComplete = remember { mutableStateOf(false) }
    val showDialogUploadComplete = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack() },
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

                    },
                    deleteImageClick = {

                    },
                    imageUrl = "",
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
                        value = textState,
                        onValueChanged = { newText, maxLength ->
                            if (newText.length <= maxLength) {
                                textState = newText
                            }
                        },
                        maxLength = 20,
                        placeholderValue = stringResource(id = R.string.my_recipewrite_title_hint),
                        imeAction = ImeAction.Next,
                        onClickTrailingicon = {
                            textState = ""
                        }
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                        text = "0/20",
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
                        value = textStateTime,
                        onValueChanged = { newText, maxLength ->
                            if (newText.length <= maxLength) {
                                textStateTime = newText
                            }
                        },
                        maxLength = 20,
                        placeholderValue = stringResource(id = R.string.my_recipewrite_time),
                        imeAction = ImeAction.Next,
                        onClickTrailingicon = {
                            textStateTime = ""
                        }
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                        text = "0/20",
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
                        value = textStateIntro,
                        onValueChanged = { newText, maxLength ->
                            if (newText.length <= maxLength) {
                                textStateIntro = newText
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
                        text = "0/100",
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
                    IngredientAndUnit(
                        valueIngredient = textStateIngredient,
                        onValueChangedIngredient = { newText, maxLength ->
                            if (newText.length <= maxLength) {
                                textStateIngredient = newText
                            }
                        },
                        placeholderValueIngredient = stringResource(id = R.string.my_recipewrite_milk),
                        maxLengthIngredient = 10,
                        imeActionIngredient = ImeAction.Default,
                        onClickTrailingiconIngredient = {
                            textStateIngredient = ""
                        },
                        onClickCancelIngredient = {

                        },
                        valueUnit = textStateUnit,
                        onValueChangedUnit = { newText, maxLength ->
                            if (newText.length <= maxLength) {
                                textStateUnit = newText
                            }
                        },
                        placeholderValueUnit = stringResource(id = R.string.my_recipewrite_hundredmilli),
                        maxLengthUnit = 10,
                        imeActionUnit = ImeAction.Default,
                        onClickTrailingiconUnit = {
                            textStateUnit = ""
                        }
                    )
                    ButtonForIngredient(
                        borderColor = ZipdabangandroidTheme.Colors.Strawberry,
                        containerColor = Color.White,
                        onClickBtn = { }
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
                            text = "1/10",
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
                    Step(
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
                        onClickAddBtn = { },
                        onClickDeleteStep = { }
                    )
                    ButtonForIngredient(
                        borderColor = ZipdabangandroidTheme.Colors.Strawberry,
                        containerColor = Color.White,
                        onClickBtn = { }
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                        text = "Step1/Step10",
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
                        value = textStateTip,
                        onValueChanged = { newText, maxLength ->
                            if (newText.length <= maxLength) {
                                textStateTip = newText
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
                        text = "0/200",
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
                            isClickedDialogSave.value = true
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


                // 알럿
                if (isClickedDialogSave.value) {
                    CustomDialogType1(
                        title = stringResource(id = R.string.my_save),
                        text = stringResource(id = R.string.my_dialog_save_detail),
                        declineText = stringResource(id = R.string.my_dialog_cancel),
                        acceptText = stringResource(id = R.string.my_save),
                        setShowDialog = {
                            showDialogSave.value = false
                        },
                        onAcceptClick = {
                            // 임시저장 api & navGraph 이동
                            showDialogSave.value = false
                        }
                    )
                }
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