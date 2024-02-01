package com.example.mariaiaandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mariaiaandroid.singleton.vm.FormBlockViewModel
import com.example.mariaiaandroid.ui.state.FormBlockUiState
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {

                    val viewModel = FormBlockViewModel(generativeModel)
                    FormBlockRoute(viewModel)
                }
            }
        }
    }
}

@Composable
internal fun FormBlockRoute(
    formBlockViewModel: FormBlockViewModel = viewModel()
) {
    val formBlockUiState by formBlockViewModel.uiState.collectAsState()

    FormBlockScreen(formBlockUiState, onSummarizeClicked = { inputText ->
        formBlockViewModel.sending(inputText)
    })
}

@Composable
fun FormBlockScreen(
    uiState: FormBlockUiState = FormBlockUiState.Initial,
    onSummarizeClicked: (String) -> Unit = {}
) {
    var value by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(top = 100.dp, start = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Maria IA",
            modifier = Modifier
        )

        TextField(
            value = value,
            onValueChange = { value = it },
            placeholder = { Text("Insira Algo") },
            modifier = Modifier
        )


        Button(
            onClick = {
                if (value.isNotBlank() && value.isNotEmpty())
                    onSummarizeClicked(value)
            }, modifier = Modifier
        ) {
            Text(
                text = "Enviar"
            )
        }

        when (uiState) {
            FormBlockUiState.Initial -> {}

            FormBlockUiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                ) {
                    CircularProgressIndicator()
                }
            }

            is FormBlockUiState.Sucess -> {
                Row(modifier = Modifier.padding(all = 8.dp)) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = "Person Icon"
                    )
                    Text(
                        text = uiState.outputText,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

            is FormBlockUiState.Error -> {
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(all = 8.dp)
                )
            }
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun FormBlockPreview() {
    MariaIaAndroidTheme {
        FormBlockScreen()
    }
}