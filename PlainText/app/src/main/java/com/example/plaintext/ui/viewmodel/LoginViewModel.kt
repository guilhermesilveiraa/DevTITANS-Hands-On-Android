package com.example.plaintext.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log

data class LoginViewState(
    val login: String = "",
    val senha: String = "",
    val checked: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    var viewState by mutableStateOf(LoginViewState())
        private set

    fun onLoginChange(newLogin: String) {
        viewState = viewState.copy(login = newLogin)
    }

    fun onSenhaChange(newSenha: String) {
        viewState = viewState.copy(senha = newSenha)
    }

    fun onCheckedChange(isChecked: Boolean) {
        viewState = viewState.copy(checked = isChecked)
    }

    // Função para verificar as credenciais recebendo-as diretamente como parâmetros
    fun checkCredentials(
        context: Context,
        navigateToList: () -> Unit,
        savedLogin: String,
        savedPassword: String
    ) {
        val login = viewState.login
        val senha = viewState.senha

        if (login == savedLogin && senha == savedPassword) {
            navigateToList()
            println("Entrei")
        } else {
            Toast.makeText(context, "Login/Senha inválidos", Toast.LENGTH_SHORT).show()
        }
    }
}
