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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.viewmodel.ListViewModel

data class EditListState(
    val nomeState: MutableState<String>,
    val usuarioState: MutableState<String>,
    val senhaState: MutableState<String>,
    val notasState: MutableState<String>,
)

@Composable
fun EditList(
    args: Screen.EditList,
    navigateBack: () -> Unit,
    savePassword: ListViewModel = hiltViewModel()
) {
    // Criação de estados mutáveis para os campos de texto, já populados com dados de args
    val stateSenha = EditListState(
        nomeState = rememberSaveable { mutableStateOf(args.password.name ?: "") },
        usuarioState = rememberSaveable { mutableStateOf(args.password.login ?: "") },
        senhaState = rememberSaveable { mutableStateOf(args.password.password ?: "") },
        notasState = rememberSaveable { mutableStateOf(args.password.notes ?: "") }
    )

    var textTitle = "Adicionar nova senha";
    if(-1 != args.password.id){
        textTitle = "Editar senha";
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

                // Componentes de entrada de dados
                EditInput("Nome", textInputState = stateSenha.nomeState)
                EditInput("Usuário", textInputState = stateSenha.usuarioState)
                EditInput("Senha", textInputState = stateSenha.senhaState)
                EditInput("Notas", textInputHeight = 100, textInputState = stateSenha.notasState)

                Spacer(modifier = Modifier.height(20.dp))

                // Botão de salvar
                Button(
                    onClick = {
                        // Criação de um objeto PasswordInfo com as informações atualizadas
                        val updatedPassword = PasswordInfo(
                            id = args.password.id, // Mantém o ID se for edição
                            name = stateSenha.nomeState.value,
                            login = stateSenha.usuarioState.value,
                            password = stateSenha.senhaState.value,
                            notes = stateSenha.notasState.value
                        )

                        // Salva a senha e atualiza a lista
                        savePassword.savePassword(updatedPassword)

                        // Volta para a tela anterior após a senha ser salva
                        navigateBack()
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
fun EditInputPreview() {
    EditInput("Nome")
}