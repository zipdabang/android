package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteContent
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteIngredient
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteRequest
import com.zipdabang.zipdabang_android.module.my.data.remote.RecipeWriteStep
import com.zipdabang.zipdabang_android.module.my.domain.usecase.PostRecipeWriteUseCase
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.Ingredient
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteDialogEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteDialogState
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteFormEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteFormState
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.Step
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class RecipeWriteViewModel @Inject constructor(
    private val dataStore: DataStore<Token>,
    private val postRecipeWriteUseCase: PostRecipeWriteUseCase,
) : ViewModel() {

    var stateRecipeWriteForm by mutableStateOf(
        RecipeWriteFormState(
            ingredients = listOf(
                Ingredient(
                    ingredientName = "",
                    quantity = "",
                )
            ),
            steps = listOf(
                Step(
                    stepImage = null,
                    description = "",
                    stepWordCount = 0,
                    textfieldEnabled = true,
                    completeBtnEnabled = false,
                    completeBtnVisible = true
                )
            ),
        ),
    )
    var stateRecipeWriteDialog by mutableStateOf(RecipeWriteDialogState())


    fun onRecipeWriteFormEvent(event : RecipeWriteFormEvent){
        when(event){
            is RecipeWriteFormEvent.TitleChanged->{
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    title = event.title,
                    titleWordCount = event.title.length
                )
            }
            is RecipeWriteFormEvent.TimeChanged->{
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    time = event.time,
                    timeWordCount = event.time.length
                )
            }
            is RecipeWriteFormEvent.IntroChanged->{
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    intro = event.intro,
                    introWordCount = event.intro.length
                )
            }
            is RecipeWriteFormEvent.RecipeTipChanged->{
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    recipeTip = event.recipeTip,
                    recipeTipWordCount = event.recipeTip.length
                )
            }
            // 썸네일
            is RecipeWriteFormEvent.ThumbnailChanged->{
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    thumbnail = event.thumbnail
                )
            }
            is RecipeWriteFormEvent.ThumbnailChangedToNull ->{
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    thumbnail = null
                )
            }


            // 재료
            is RecipeWriteFormEvent.BtnIngredientAdd->{
                val currentIngredients = stateRecipeWriteForm.ingredients.toMutableList()
                val newIngredient = Ingredient(
                    ingredientName = "",
                    quantity = "",
                )
                if(currentIngredients.size < 10){
                    currentIngredients.add(newIngredient)
                    stateRecipeWriteForm = stateRecipeWriteForm.copy(
                        ingredients = currentIngredients,
                        ingredientsNum = currentIngredients.size
                    )
                }
            }
            is RecipeWriteFormEvent.BtnIngredientDelete ->{
                val currentIngredients = stateRecipeWriteForm.ingredients.toMutableList()
                currentIngredients.removeAt(event.ingredientNum - 1)

                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    ingredients = currentIngredients,
                    ingredientsNum = currentIngredients.size
                )
            }
            is RecipeWriteFormEvent.IngredientChanged->{
                val ingredientNumToChange = event.ingredientNum
                val newIngredientName = event.ingredientName

                val currentIngredients = stateRecipeWriteForm.ingredients.toMutableList()
                val updatedIngredient = currentIngredients[ingredientNumToChange -1].copy(
                    ingredientName = newIngredientName,
                )

                currentIngredients[ingredientNumToChange -1] = updatedIngredient
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    ingredients = currentIngredients
                )
            }
            is RecipeWriteFormEvent.QuantityChanged->{
                val quantityNumToChange = event.quantityNum
                val newQuantityName = event.quantityName

                val currentIngredients = stateRecipeWriteForm.ingredients.toMutableList()
                val updatedIngredient = currentIngredients[quantityNumToChange-1].copy(
                    quantity = newQuantityName
                )

                currentIngredients[quantityNumToChange-1] = updatedIngredient
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    ingredients = currentIngredients
                )
            }
            is RecipeWriteFormEvent.BtnIngredientAddEnabled->{
                var allIngredientsValid = true // 모든 재료가 유효한지 여부를 저장할 변수

                for (i in 0 until event.ingredientNum) {
                    if (stateRecipeWriteForm.ingredients[i].ingredientName.isEmpty() || stateRecipeWriteForm.ingredients[i].quantity.isEmpty()) {
                        allIngredientsValid = false
                        break
                    }
                }

                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    ingredientBtnEnabled = allIngredientsValid
                )
            }


            // 스텝
            is RecipeWriteFormEvent.BtnStepAdd->{
                val stepNumToChange = event.stepNum
                val currentSteps = stateRecipeWriteForm.steps.toMutableList()
                val preStep = currentSteps[stepNumToChange - 1].copy(
                    textfieldEnabled = false,
                    completeBtnVisible = false,
                )
                val newStep = Step(
                    stepImage = null,
                    description = "",
                    stepWordCount = 0,
                    textfieldEnabled = true,
                    completeBtnEnabled = false,
                    completeBtnVisible = true
                )
                currentSteps[stepNumToChange - 1] = preStep
                if(stepNumToChange <= 10){
                    currentSteps.add(newStep)
                }

                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps,
                    stepsNum = currentSteps.size
                )
            }
            is RecipeWriteFormEvent.BtnStepDelete ->{
                val stepNumToDelete = event.stepNum
                val currentSteps = stateRecipeWriteForm.steps.toMutableList()

                if(currentSteps.size != 1){
                    currentSteps.removeAt(stepNumToDelete -1 )
                    stateRecipeWriteForm = stateRecipeWriteForm.copy(
                        steps = currentSteps,
                        stepsNum = currentSteps.size
                    )
                }
            }
            is RecipeWriteFormEvent.BtnStepEdit ->{
                val stepNumToEdit = event.stepNum
                val currentSteps = stateRecipeWriteForm.steps.toMutableList()

                val editStep = currentSteps[stepNumToEdit - 1].copy(
                    textfieldEnabled = true,
                    completeBtnVisible = true,
                    completeBtnEnabled = true,
                )
                currentSteps[stepNumToEdit -1] = editStep

                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps,
                    stepsNum = currentSteps.size
                )
            }
            is RecipeWriteFormEvent.StepChanged->{
                val stepNumToChange = event.stepNum
                val newStepDescription = event.stepDescription

                val currentSteps = stateRecipeWriteForm.steps.toMutableList()
                val updatedStep = currentSteps[stepNumToChange -1].copy(
                    description = newStepDescription,
                    stepWordCount = newStepDescription.length
                )
                Log.e("recipewriteform","${updatedStep}")
                currentSteps[stepNumToChange -1] = updatedStep
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps
                )
            }
            is RecipeWriteFormEvent.StepImageChanged->{
                val stepNumToChange = event.stepNum
                val newStepImage = event.stepImage

                val currentSteps = stateRecipeWriteForm.steps.toMutableList()
                val updatedStep = currentSteps[stepNumToChange - 1].copy(
                    stepImage = newStepImage
                )

                currentSteps[stepNumToChange - 1] = updatedStep
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps
                )
            }
            is RecipeWriteFormEvent.StepImageChangedToEmpty->{
                val stepNumToChange = event.stepNum
                val newStepImage = event.stepImage

                val currentSteps = stateRecipeWriteForm.steps.toMutableList()
                val updatedStep = currentSteps[stepNumToChange - 1].copy(
                    stepImage = newStepImage
                )

                currentSteps[stepNumToChange - 1] = updatedStep
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps
                )
            }
            is RecipeWriteFormEvent.StepIsValidate->{
                val stepNumToChange = event.stepNum

                val currentSteps = stateRecipeWriteForm.steps.toMutableList()

                // 스텝의 유효성을 개별적으로 확인
                val stepIsValid = stateRecipeWriteForm.steps[stepNumToChange - 1].description.isNotEmpty()
                        && stateRecipeWriteForm.steps[stepNumToChange - 1].stepImage != null

                // 해당 스텝의 completeBtnEnabled 업데이트
                val updatedStep = currentSteps[stepNumToChange - 1].copy(
                    completeBtnEnabled = stepIsValid
                )

                currentSteps[stepNumToChange - 1] = updatedStep

                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps
                )
            }

            is RecipeWriteFormEvent.BtnEnabled->{

            }
        }
    }

    fun onRecipeWriteDialogEvent(event : RecipeWriteDialogEvent){
        when(event){
            is RecipeWriteDialogEvent.RecipeDeleteChanged->{
                stateRecipeWriteDialog = stateRecipeWriteDialog.copy(isOpenRecipeDelete = event.isOpen)
            }
            is RecipeWriteDialogEvent.FileSelectChanged->{
                stateRecipeWriteDialog = stateRecipeWriteDialog.copy(isOpenFileSelect = event.isOpen)
            }
            is RecipeWriteDialogEvent.StepFileSelectChanged->{
                stateRecipeWriteDialog = stateRecipeWriteDialog.copy(isOpenStepFileSelect = event.isOpen, stepNum = event.stepNum)
            }
            is RecipeWriteDialogEvent.PermissionChanged->{
                stateRecipeWriteDialog = stateRecipeWriteDialog.copy(isOpenPermission = event.isOpen)
            }
            is RecipeWriteDialogEvent.SaveChanged->{
                stateRecipeWriteDialog = stateRecipeWriteDialog.copy(isOpenSave = event.isOpen)
            }
            is RecipeWriteDialogEvent.UploadCategoryChanged->{
                stateRecipeWriteDialog = stateRecipeWriteDialog.copy(isOpenUploadCategory = event.isOpen)
            }
            is RecipeWriteDialogEvent.UploadCompleteChanged->{
                stateRecipeWriteDialog = stateRecipeWriteDialog.copy(isOpenUploadComplete = event.isOpen)
            }
        }
    }


    suspend fun postRecipeWrite(){
        val ingredients = stateRecipeWriteForm.ingredients.map { ingredient ->
            RecipeWriteIngredient(
                ingredientName = ingredient.ingredientName,
                quantity = ingredient.quantity
            )
        }
        val steps = stateRecipeWriteForm.steps.mapIndexed { index, step ->
            RecipeWriteStep(
                description = step.description,
                stepNum = index + 1
            )
        }
        val stepImages = stateRecipeWriteForm.steps.map { step ->
            step.stepImage as? String ?: ""
        }
        try{
            val result = postRecipeWriteUseCase(
                accessToken = "Bearer " + dataStore.data.first().accessToken.toString(),
                recipeWriteForm = RecipeWriteRequest(
                    content = RecipeWriteContent(
                        categoryId = listOf(1,2),
                        ingredientCount = stateRecipeWriteForm.ingredientsNum,
                        ingredients = ingredients,
                        intro = stateRecipeWriteForm.intro,
                        name = stateRecipeWriteForm.title,
                        recipeTip = stateRecipeWriteForm.recipeTip,
                        stepCount = stateRecipeWriteForm.stepsNum,
                        steps = steps,
                        time = stateRecipeWriteForm.time
                    ),
                    stepImages = stepImages,
                    thumbnail = stateRecipeWriteForm.thumbnail.toString()
                )
            )

            result.collect{result->
                when(result){
                    is Resource.Success->{
                        Log.e("recipewrite", "api는 성공 : ${result.message} ${result.code}")
                        if(result.code == 2000){
                            Log.e("recipewrite", "성공 : ${result.message}")
                        }
                    }
                    is Resource.Error ->{
                        Log.e("recipewrite", "에러 : ${result.message}")
                    }
                    is Resource.Loading ->{
                        Log.e("recipewrite", "로딩중 : ${result.data}")
                    }
                }
            }
        }
        catch (e: Exception) {}
    }



}