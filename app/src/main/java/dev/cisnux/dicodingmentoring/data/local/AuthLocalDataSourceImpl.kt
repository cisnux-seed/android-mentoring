package dev.cisnux.dicodingmentoring.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthLocalDataSourceImpl
@Inject constructor(private val dataStore: DataStore<Preferences>) : AuthLocalDataSource {

    override fun getAuthSession(id: String): Flow<Boolean> {
        return dataStore.data.map { preference ->
            val key = booleanPreferencesKey(id)
            val data = preference[key] ?: false
            Log.d(AuthLocalDataSourceImpl::class.simpleName, data.toString())
            data
        }
    }

    override suspend fun saveAuthSession(id: String, session: Boolean): Unit =
        withContext(Dispatchers.IO) {
            val key = booleanPreferencesKey(id)
            dataStore.edit { preference ->
                preference[key] = session
            }
        }

    override suspend fun deleteSession(id: String): Unit = withContext(Dispatchers.IO) {
        val key = booleanPreferencesKey(id)
        dataStore.edit { preference ->
            preference.remove(key)
        }
    }
}