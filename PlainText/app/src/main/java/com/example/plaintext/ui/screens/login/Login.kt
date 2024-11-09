package com.example.plaintext.ui.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.R
import com.example.plaintext.ui.viewmodel.LoginViewModel
import com.example.plaintext.ui.viewmodel.PreferencesViewModel

@Composable
fun Login_screen(
    navigateToSettings: () -> Unit,
    navigateToList: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel(),
    preferencesViewModel: PreferencesViewModel = hiltViewModel()
) {
    val state = loginViewModel.viewState
    val context = LocalContext.current

    Scaffold(
        topBar = { TopBarComponent(navigateToSettings = navigateToSettings) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2C1B16))
                .padding(padding)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(Color.Green),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = "The most secure Password Manager" +
                            " Bob and Alice",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Main content
            Text(
                text = "Digite suas credenciais para continuar",
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(60.dp))

            OutlinedTextField(
                value = state.login,
                onValueChange = { loginViewModel.onLoginChange(it) },
                label = { Text(text = "Nome de Usuário", color = Color.White) },
                textStyle = LocalTextStyle.current.copy(color = Color.White), // Define a cor do texto preenchido como branca
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(
                value = state.senha,
                onValueChange = { loginViewModel.onSenhaChange(it) },
                label = { Text(text = "Senha", color = Color.White) },
                textStyle = LocalTextStyle.current.copy(color = Color.White), // Define a cor do texto preenchido como branca
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.checked,
                    onCheckedChange = { loginViewModel.onCheckedChange(it) }
                )
                Text(
                    text = "Salvar as informações de login",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    loginViewModel.checkCredentials(
                        context,
                        navigateToList,
                        savedLogin = preferencesViewModel.preferencesState.login,
                        savedPassword = preferencesViewModel.preferencesState.password
                    )
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp),
            ) {
                Text("Enviar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUI() {
    Login_screen(
        navigateToSettings = {},
        navigateToList = {}
    )
}

@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = { Text(text = "Sobre") },
            text = { Text(text = "PlainText Password Manager v1.0") },
            confirmButton = {
                Button(
                    onClick = { shouldShowDialog.value = false }
                ) {
                    Text(text = "Ok")
                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarComponent(
    navigateToSettings: (() -> Unit)? = null,
    navigateToSensores: (() -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val shouldShowDialog = remember { mutableStateOf(false) }

    if (shouldShowDialog.value) {
        MyAlertDialog(shouldShowDialog = shouldShowDialog)
    }

    TopAppBar(
        title = { Text("PlainText") },
        actions = {
            // Botão de três pontos para abrir o menu de opções
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // Opção para Configurações
                DropdownMenuItem(
                    text = { Text("Preferências") },
                    onClick = {
                        navigateToSettings?.invoke()
                        expanded = false
                        Log.d("Navigation", "Navigating to Preferences")
                    },
                    modifier = Modifier.padding(8.dp)
                )
                // Opção para Sensores, se `navigateToSensores` não for nulo
                navigateToSensores?.let {
                    DropdownMenuItem(
                        text = { Text("Sensores") },
                        onClick = {
                            it()
                            expanded = false
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
                // Opção para exibir o diálogo "Sobre"
                DropdownMenuItem(
                    text = { Text("Sobre") },
                    onClick = {
                        shouldShowDialog.value = true
                        expanded = false
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    )
}
