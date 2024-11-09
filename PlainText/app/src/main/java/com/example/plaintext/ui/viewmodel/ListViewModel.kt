package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.data.repository.PasswordDBStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListViewState(
    var passwordList: List<PasswordInfo>,
    var isCollected: Boolean = false
)

@HiltViewModel
class ListViewModel @Inject constructor(
    private val passwordDBStore: PasswordDBStore
) : ViewModel() {

    var listViewState by mutableStateOf(ListViewState(passwordList = emptyList()))
        private set

    init {
        viewModelScope.launch {
            passwordDBStore.getList().collect { passwords ->
                listViewState = ListViewState(passwordList = passwords, isCollected = true)
            }
        }
    }

    fun savePassword(passwordInfo: PasswordInfo) {
        viewModelScope.launch {
            passwordDBStore.save(passwordInfo)

            // Atualiza a lista de senhas na interface
            passwordDBStore.getList().collect { passwords ->
                listViewState = ListViewState(passwordList = passwords, isCollected = true)
            }
        }
    }

}
