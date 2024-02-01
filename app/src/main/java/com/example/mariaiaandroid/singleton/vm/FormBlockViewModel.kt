package com.example.mariaiaandroid.singleton.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mariaiaandroid.ui.state.FormBlockUiState
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FormBlockViewModel(
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val _uiState: MutableStateFlow<FormBlockUiState> =
        MutableStateFlow(FormBlockUiState.Initial)
    val uiState: StateFlow<FormBlockUiState> =
        _uiState.asStateFlow()

    fun sending(inputText: String) {
        _uiState.value = FormBlockUiState.Loading

        val prompt = "Send that form for me: $inputText"

        viewModelScope.launch {
            try {
                val response = generativeModel.generateContent(prompt) //TODO try a chat Response other time
                response.text?.let { outputContent ->
                    _uiState.value = FormBlockUiState.Sucess(outputContent)
                }
            } catch (e: Exception) {
                _uiState.value = FormBlockUiState.Error("Error: ${e.localizedMessage}")
            }
        }

    }
}