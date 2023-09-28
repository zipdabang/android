package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.Ingredient
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteDialogEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteDialogState
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteFormEvent
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.RecipeWriteFormState
import com.zipdabang.zipdabang_android.module.my.ui.state.recipewrite.Step
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeWriteViewModel @Inject constructor(
    private val dataStore: DataStore<Token>,
) : ViewModel() {

    var stateRecipeWriteForm by mutableStateOf(
        RecipeWriteFormState(
            steps = listOf(
                Step(
                    stepNum = 1,
                    stepImages = null,
                    description = "",
                    stepWordCount = 0
                )
            ),
            ingredients = listOf(
                Ingredient(
                    ingredientNum = 1,
                    ingredientName = "",
                    ingredientWordCount = 0,
                    quantity = "",
                    quantityWordCount = 0,
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
            is RecipeWriteFormEvent.ThumbnailChanged->{

            }
            is RecipeWriteFormEvent.BtnIngredientAdd->{
                val currentIngredients = stateRecipeWriteForm.ingredients.toMutableList()
                val newIngredient = Ingredient(
                    ingredientNum = stateRecipeWriteForm.ingredientsNum + 1,
                    ingredientName = "",
                    ingredientWordCount = 0,
                    quantity = "",
                    quantityWordCount = 0,
                )
                currentIngredients.add(newIngredient)
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    ingredients = currentIngredients,
                    ingredientsNum = currentIngredients.size
                )
            }
            is RecipeWriteFormEvent.IngredientChanged->{
                stateRecipeWriteForm = stateRecipeWriteForm.copy(

                )
            }
            is RecipeWriteFormEvent.BtnStepAdd->{
                val currentSteps = stateRecipeWriteForm.steps.toMutableList()
                val newStep = Step(
                    stepNum = stateRecipeWriteForm.stepsNum + 1,
                    stepImages = null,
                    description = "",
                    stepWordCount = 0
                )
                currentSteps.add(newStep)
                stateRecipeWriteForm = stateRecipeWriteForm.copy(
                    steps = currentSteps,
                    stepsNum = currentSteps.size
                )
            }
            is RecipeWriteFormEvent.StepChanged->{

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





}