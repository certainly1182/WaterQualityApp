package me.certainly1182.waterqualityapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
    ) : DataStoreManagement {
    companion object {
        const val TEMPERATURE_OFFSET_KEY = "temperature_offset"
        val temperatureOffsetKey = floatPreferencesKey(TEMPERATURE_OFFSET_KEY)
    }

    override suspend fun updateTemperatureOffset(temperatureOffset: Float) {
        dataStore.edit { preferences ->
            preferences[temperatureOffsetKey] = temperatureOffset
        }
    }

    override fun getTemperatureOffset(): Flow<Float> {
        val temperatureOffsetFlow: Flow<Float> = dataStore.data.map { preferences ->
            preferences[temperatureOffsetKey] ?: 0f
        }
        return temperatureOffsetFlow
    }
}

interface DataStoreManagement {
    suspend fun updateTemperatureOffset(temperatureOffset: Float)
    fun getTemperatureOffset(): Flow<Float>
}
