package com.example.plaintext.ui.screens.preferences


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.util.PreferenceInput
import com.example.plaintext.ui.screens.util.PreferenceItem
import com.example.plaintext.ui.viewmodel.PreferencesState
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import kotlinx.coroutines.delay

@Composable
fun SettingsScreen(viewModel: PreferencesViewModel = hiltViewModel()
){
    Scaffold(
        topBar = {
            TopBarComponent()
        }
    ){ padding ->
        SettingsContent(
            modifier = Modifier.padding(padding),
            viewModel.preferencesState,
            viewModel::updateLogin,
            viewModel::updatePassword,
            viewModel::updatePreencher)
    }
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    state: PreferencesState,
    updateLogin: (login: String) -> Unit,
    updatePassword: (password: String) -> Unit,
    updatePreencher: (state: Boolean) -> Unit,

    ) {

    val preferencesState = state

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())){

        PreferenceInput(
            title = "Preencher Login",
            label = "Login",
            fieldValue = preferencesState.login,
            summary = "Preencher login na tela inicial"
        ){ newLogin ->
            updateLogin(newLogin)
        }

        PreferenceInput(
            title = "Setar Senha",
            label = "Label",
            fieldValue = preferencesState.password,
            summary = "Senha para entrar no sistema"
        ){ newPassword ->
            updatePassword(newPassword) // Atualiza a senha
        }

        PreferenceItem(
            title = "Preencher Login",
            summary = "Preencher login na tela inicial",
            onClick = {
                // deve alterar o estado que representa se o switch está ligado ou não
            },
            control = {
                Switch(
                    checked = preferencesState.preencher, // deve ler o estado que representa se o switch está ligado ou não
                    onCheckedChange = { isChecked ->
                        // deve alterar o estado que representa se o switch está ligado ou não
                        updatePreencher(isChecked)
                    }
                )
            }
        )
    }
}