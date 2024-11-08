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
    val loginViewModel : LoginViewModel = hiltViewModel()
    val preferencesViewModel : PreferencesViewModel = hiltViewModel()
    var listView : ListViewModel = hiltViewModel()

    NavHost(
        navController = appState.navController,
        startDestination = Screen.Login,
    ) {
        composable<Screen.Hello>{
            var args = it.toRoute<Screen.Hello>()
            Hello_screen(args)
        }

        composable<Screen.Preferences> {
            SettingsScreen(
                viewModel = preferencesViewModel
            )
        }

        composable<Screen.List> {
            val samplePasswords = listOf(
                PasswordInfo(id = 1, name = "Twitter", login = "dev", password = "senha123", notes = "Nota1"),
                PasswordInfo(id = 2, name = "Facebook", login = "devtitans", password = "senha456", notes = "Nota2"),
                PasswordInfo(id = 3, name = "Moodle", login = "dev.com", password = "senha789", notes = "Nota3")
            )

            val listState = ListViewState(
                passwordList = samplePasswords,
                isCollected = true
            )

            ListView(
                listState = listState,
                navigateToEdit = { passwordInfo ->
                    // Passando o PasswordInfo para a tela de edição
                    appState.navigateToEditList(passwordInfo)
                },
                onAddClick = {
                    // Passando um PasswordInfo vazio ao adicionar um novo item
                    appState.navigateToEditList(PasswordInfo(-1, "", "", "", ""))
                }
            )
        }

        composable<Screen.Login>{
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
                navigateBack = {},
                savePassword =  listView
            )
        }
    }
}
