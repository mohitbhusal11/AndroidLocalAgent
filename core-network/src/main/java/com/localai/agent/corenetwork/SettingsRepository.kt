package com.localai.agent.corenetwork

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.localai.agent.core.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(Constants.DATASTORE_NAME)

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val forceLocalKey = stringPreferencesKey(Constants.KEY_FORCE_LOCAL_ASSISTANT)
    private val darkModeKey = stringPreferencesKey(Constants.KEY_DARK_MODE)

    val forceLocalAssistant: Flow<Boolean> =
        context.dataStore.data.map { it[forceLocalKey] == "true" }
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { it[darkModeKey] != "false" }

    suspend fun setForceLocalAssistant(enabled: Boolean) {
        context.dataStore.edit { it[forceLocalKey] = enabled.toString() }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[darkModeKey] = enabled.toString() }
    }

    suspend fun isForceLocalAssistantSync(): Boolean = forceLocalAssistant.first()
}
