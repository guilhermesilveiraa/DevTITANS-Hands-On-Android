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
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.screens.login.TopBarComponent

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
    savePassword: (password: PasswordInfo) -> Unit
) {

    val stateSenha = EditListState(
        nomeState = rememberSaveable {mutableStateOf(args.password.name)} ,
        usuarioState = rememberSaveable {mutableStateOf(args.password.login)},
        senhaState = rememberSaveable {mutableStateOf(args.password.password)},
        notasState = rememberSaveable {mutableStateOf(args.password.notes)}
    )

    Scaffold(

        topBar = {
            Box(
                Modifier
                    .background(Color(0xFF2C1B16))
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            ){
                Text("Plain Text", color = Color.White,fontSize = 20.sp,)
            }
        },

        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF2C1B16)), // Fundo marrom escuro

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .background(Color(0xFFB2EB40))
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 16.dp)

                ){
                    Text("Adicionar nova Senha",
                        color = Color.White,
                        fontSize = 20.sp,

                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                EditInput("Nome", textInputState = stateSenha.nomeState)
                EditInput("Usuário",  textInputState = stateSenha.usuarioState)
                EditInput("Senha",  textInputState = stateSenha.senhaState)
                EditInput("Notas", textInputHeight = 100,  textInputState = stateSenha.notasState)

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        // Aqui vai o código para salvar a senha
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFA26B), // Cor de fundo do botão
                        contentColor = Color(0xFF4A2C1F)    // Cor do texto do botão
                    ),

                    modifier = Modifier
                        .padding(16.dp)

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
fun EditListPreview() {
    EditList(
        Screen.EditList(PasswordInfo(1, "", "", "", "")),
        navigateBack = {},
        savePassword = {}
    )
}

@Preview(showBackground = true)
@Composable
fun EditInputPreview() {
    EditInput("Nome")
}