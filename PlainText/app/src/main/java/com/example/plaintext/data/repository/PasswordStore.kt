package com.example.plaintext.data.repository

import com.example.plaintext.data.dao.PasswordDao
import com.example.plaintext.data.model.Password
import com.example.plaintext.data.model.PasswordInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PasswordDBStore {
    fun getList(): Flow<List<PasswordInfo>>
    suspend fun add(password: Password): Long
    suspend fun update(password: Password)
    suspend fun get(id: Int): Password?
    suspend fun save(passwordInfo: PasswordInfo)
    suspend fun isEmpty(): Flow<Boolean>
    suspend fun getAllPasswords(): Flow<List<Password>>
}

class LocalPasswordDBStore(
    private val passwordDao: PasswordDao
) : PasswordDBStore {

    override fun getList(): Flow<List<PasswordInfo>> {
        return passwordDao.getAllPasswords().map { passwords ->
            passwords.map { password ->
                PasswordInfo(
                    id = password.id,
                    name = password.name,
                    login = password.login,
                    password = password.password,
                    notes = password.notes ?: ""
                )
            }
        }
    }

    override suspend fun add(password: Password): Long {
        return passwordDao.addPassword(password)
    }

    override suspend fun update(password: Password) {
        passwordDao.updatePassword(password)
    }

    override suspend fun get(id: Int): Password? {
        return passwordDao.getPasswordById(id)
    }

    override suspend fun getAllPasswords(): Flow<List<Password>> {
        return passwordDao.getAllPasswords()
    }

    override suspend fun save(passwordInfo: PasswordInfo) {
        val password = Password(
            id = if (passwordInfo.id == -1) 0 else passwordInfo.id, // Define id como 0 para novas senhas
            name = passwordInfo.name,
            login = passwordInfo.login,
            password = passwordInfo.password,
            notes = passwordInfo.notes
        )

        if (passwordInfo.id == -1) { // Insere nova senha se o ID for -1
            add(password)
        } else { // Atualiza senha existente
            update(password)
        }
    }



    override suspend fun isEmpty(): Flow<Boolean> {
        return passwordDao.getAllPasswords().map { it.isEmpty() }
    }
}
