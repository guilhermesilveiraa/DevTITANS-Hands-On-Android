package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.plaintext.data.model.PasswordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class EditListViewState(
    val name: String = "",
    val login: String = "",
    val password: String = "",
    val notes: String = ""
)

@HiltViewModel
class EditListViewModel @Inject constructor() : ViewModel() {

    var viewState by mutableStateOf(EditListViewState())
        private set

    fun initializeState(passwordInfo: PasswordInfo) {
        viewState = viewState.copy(
            name = passwordInfo.name,
            login = passwordInfo.login,
            password = passwordInfo.password,
            notes = passwordInfo.notes
        )
    }

    fun onNameChange(newName: String) {
        viewState = viewState.copy(name = newName)
    }

    fun onLoginChange(newLogin: String) {
        viewState = viewState.copy(login = newLogin)
    }

    fun onPasswordChange(newPassword: String) {
        viewState = viewState.copy(password = newPassword)
    }

    fun onNotesChange(newNotes: String) {
        viewState = viewState.copy(notes = newNotes)
    }

    fun savePasswordInfo(): PasswordInfo {
        return PasswordInfo(
            id = -1,
            name = viewState.name,
            login = viewState.login,
            password = viewState.password,
            notes = viewState.notes
        )
    }
}