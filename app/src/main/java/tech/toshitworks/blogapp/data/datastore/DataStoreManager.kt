package tech.toshitworks.blogapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
): PreferenceManager{
    override suspend fun saveToken(token: String) {
        dataStore.edit {
            it[PreferencesKey.TOKEN] = token
        }
    }

    override fun getToken(): Flow<String?> {
        return dataStore.data.map {
            it[PreferencesKey.TOKEN]
        }
    }

    override suspend fun saveOnboardingViewed() {
        dataStore.edit {
            it[PreferencesKey.ONBOARDING_VIEWED] = true
        }
    }

    override fun getOnboardingViewed(): Flow<Boolean?> {
        return dataStore.data.map {
            it[PreferencesKey.ONBOARDING_VIEWED]
        }
    }

    override suspend fun saveCategories(set: Set<String>) {
        dataStore.edit {
            it[PreferencesKey.CATEGORIES] = set
            println("saved in dsp")
        }
    }

    override fun getCategories(): Flow<Set<String>> {
        return  dataStore.data.map {
            it[PreferencesKey.CATEGORIES] ?: emptySet()
        }

    }
}