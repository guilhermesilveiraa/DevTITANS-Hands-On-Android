package com.example.plaintext.ui.screens.editList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.sp
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.screens.login.TopBarComponent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.ui.viewmodel.EditListViewModel

data class EditListState(
    val nomeState: MutableState<String>,
    val usuarioState: MutableState<String>,
    val senhaState: MutableState<String>,
    val notasState: MutableState<String>,
)

fun isPasswordEmpty(password: PasswordInfo): Boolean {
    return password.name.isEmpty() && password.login.isEmpty() && password.password.isEmpty() && password.notes.isEmpty()
}

@Composable
fun EditList(
    args: Screen.EditList,
    navigateBack: () -> Unit,
    editListViewModel: EditListViewModel = hiltViewModel()
) {
    // Inicializa o estado do ViewModel com os dados do argumento
    LaunchedEffect(Unit) {
        editListViewModel.initializeState(args.password)
    }

    val state = editListViewModel.viewState

    var textTitle = "Adicionar nova senha"
    if (args.password.id != -1) {
        textTitle = "Editar senha"
    }

    Scaffold(
        topBar = {
            Box(
                Modifier
                    .background(Color(0xFF2C1B16))
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                Text("Plain Text", color = Color.White, fontSize = 20.sp)
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF2C1B16)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .background(Color(0xFFB2EB40))
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 16.dp)
                ) {
                    Text(textTitle, color = Color.White, fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Componentes de entrada de dados, agora usando o ViewModel para gerenciar o estado
                EditInput("Nome", textInputState = state.name, onValueChange = { editListViewModel.onNameChange(it) })
                EditInput("Usuário", textInputState = state.login, onValueChange = { editListViewModel.onLoginChange(it) })
                EditInput("Senha", textInputState = state.password, onValueChange = { editListViewModel.onPasswordChange(it) })
                EditInput("Notas", textInputHeight = 100, textInputState = state.notes, onValueChange = { editListViewModel.onNotesChange(it) })

                Spacer(modifier = Modifier.height(20.dp))

                // Botão de salvar
                Button(
                    onClick = {
                        val updatedPassword = editListViewModel.savePasswordInfo()
                        navigateBack() // Volta para a tela anterior
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFA26B),
                        contentColor = Color(0xFF4A2C1F)
                    ),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Salvar", fontSize = 16.sp)
                }
            }
        }
    )
}

@Composable
fun EditInput(
    textInputLabel: String,
    textInputState: String,
    onValueChange: (String) -> Unit,
    textInputHeight: Int = 60
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(textInputHeight.dp)
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = textInputState,
            onValueChange = onValueChange,
            label = { Text(textInputLabel, color = Color.White) },
            modifier = Modifier
                .height(textInputHeight.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}




@Composable
fun EditInput(
    textInputLabel: String,
    textInputState: MutableState<String> = mutableStateOf(""),
    textInputHeight: Int = 60
) {
    val padding: Int = 30

    var textState by rememberSaveable { textInputState }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(textInputHeight.dp)
            .padding(horizontal = padding.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text(textInputLabel, color = Color.White)},
            modifier = Modifier
                .height(textInputHeight.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.White)
        )

    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun EditListPreview() {
    EditList(
        Screen.EditList(PasswordInfo(1, "", "", "", "")),
        navigateBack = {},
        //savePassword = {}
    )
}

@Preview(showBackground = true)
@Composable
fun EditInputPreview() {
    EditInput("Nome")
}