package dev.cisnux.dicodingmentoring.data.local

import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    fun getAuthSession(id: String): Flow<Boolean>
    suspend fun saveAuthSession(id: String, session: Boolean)

    suspend fun deleteSession(id: String)
}