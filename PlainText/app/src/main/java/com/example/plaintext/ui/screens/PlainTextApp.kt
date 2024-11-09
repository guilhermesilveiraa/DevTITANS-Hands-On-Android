package com.example.plaintext.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.editList.EditList
import com.example.plaintext.ui.screens.hello.Hello_screen
import com.example.plaintext.ui.screens.list.AddButton
import com.example.plaintext.ui.screens.list.ListView
import com.example.plaintext.ui.screens.login.Login_screen
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.preferences.SettingsScreen
import com.example.plaintext.ui.viewmodel.ListViewModel
import com.example.plaintext.ui.viewmodel.ListViewState
import com.example.plaintext.ui.viewmodel.LoginViewModel
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import com.example.plaintext.utils.parcelableType
import kotlin.reflect.typeOf

@Composable
fun PlainTextApp(
    appState: JetcasterAppState = rememberJetcasterAppState()
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val preferencesViewModel: PreferencesViewModel = hiltViewModel()
    val listView: ListViewModel = hiltViewModel()

    NavHost(
        navController = appState.navController,
        startDestination = Screen.Login,
    ) {
        composable<Screen.Hello> {
            val args = it.toRoute<Screen.Hello>()
            Hello_screen(args)
        }

        composable<Screen.Preferences> {
            SettingsScreen(viewModel = preferencesViewModel)
        }

        composable<Screen.List> {
            // Observa o estado atual da lista a partir do ListViewModel
            val listState = listView.listViewState

            ListView(
                listState = listState,
                navigateToEdit = { passwordInfo ->
                    // Navega para a tela de edição com o PasswordInfo selecionado
                    appState.navigateToEditList(passwordInfo)
                },
                onAddClick = {
                    appState.navigateToEditList(PasswordInfo(-1, "", "", "", ""))
                },
                navigateToSettings = { appState.navController.navigate(Screen.Preferences) } // Adicionando navegação
            )

        }

        composable<Screen.Login> {
            Login_screen(
                navigateToSettings = { appState.navController.navigate(Screen.Preferences) },
                navigateToList = { appState.navController.navigate(Screen.List) },
                loginViewModel = loginViewModel,
                preferencesViewModel = preferencesViewModel,
            )
        }

        composable<Screen.EditList>(
            typeMap = mapOf(typeOf<PasswordInfo>() to parcelableType<PasswordInfo>())
        ) {
            val args = it.toRoute<Screen.EditList>()
            EditList(
                args,
                navigateBack = {
                    appState.navController.popBackStack() // Retorna à tela anterior
                },
                savePassword = listView
            )
        }
    }
}

