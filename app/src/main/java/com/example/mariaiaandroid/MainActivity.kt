package com.example.mariaiaandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mariaiaandroid.singleton.ViewModel.TextFieldState
import com.example.mariaiaandroid.ui.theme.MariaIaAndroidTheme
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val config = generationConfig {
            temperature = 0.9f
        }
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.apiKey,
            generationConfig = config

        )

        setContent {
            MariaIaAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyForm(
                        name = "Gemini",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyForm(
    name: String,
    modifier: Modifier = Modifier,
    value: TextFieldState = remember { TextFieldState() },
) {
    Column {
        Text(
            text = name,
            modifier = modifier.padding(start = 10.dp, top = 40.dp)
        )

        TextField(
            value = value.text,
            onValueChange = { value.text = it },
            placeholder = { Text("Insira Algo") },
            modifier = modifier
        )

        Button(
            onClick = { }, modifier = modifier
        ) {
            Text(
                text = "Enviar"
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MyFormPreview() {
    MariaIaAndroidTheme {
        MyForm("Gemini")
    }
}