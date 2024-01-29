package com.example.mariaiaandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                        modifier = Modifier.padding(innerPadding),
                        value = "teste",
                        trial = { input ->
                            print(input)
                        }
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
    value: String,
    onValueChanged: (String) -> Unit
) {
    val initialValue = rememberUpdatedState(value)

    Column {
        Text(
            text = name,
            modifier = modifier
        )

        TextField(
            value = initialValue.value,
            onValueChange = { initialValue.value = it },
            placeholder = { Text("Insira Algo") }
        )

        Button(onClick = { /*TODO*/ }) {
            Text(
                text = "Enviar",
                modifier = modifier
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MyFormPreview() {
    MariaIaAndroidTheme {
        MyForm("Insira Algo", Modifier.fillMaxSize()) { input -> print(input) }

    }
}