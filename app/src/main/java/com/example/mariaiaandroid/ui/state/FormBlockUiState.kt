package com.example.mariaiaandroid.ui.state

sealed interface FormBlockUiState {

    data object Initial : FormBlockUiState
    data object Loading : FormBlockUiState

    data class Sucess(
        val outputText: String
    ) : FormBlockUiState

    data class Error(
        val errorMessage: String
    ) : FormBlockUiState
}