package com.example.mariaiaandroid.singleton.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TextFieldState {
    var text: String by mutableStateOf("")
}
