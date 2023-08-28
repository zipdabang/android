package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.detail.recipe.use_case.GetRecipeDetailUseCase
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RecipeDetailViewModel @Inject constructor(
    private val getRecipeDataUseCase: GetRecipeDetailUseCase
) : ViewModel() {

    private val _recipeDetailState = mutableStateOf(RecipeDetailState())
    val recipeDetailState: State<RecipeDetailState> = _recipeDetailState

    fun getRecipeDetail(recipeId: Int) {
        getRecipeDataUseCase(recipeId).onEach { result ->
            result.data?.let {
                when (result) {
                    is Resource.Loading -> {
                        _recipeDetailState.value = RecipeDetailState(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _recipeDetailState.value = RecipeDetailState(
                            isLoading = false,
                            recipeDetailData = it
                        )
                    }
                    is Resource.Error -> {
                        _recipeDetailState.value = RecipeDetailState(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }
                }
            }
        }
    }
}