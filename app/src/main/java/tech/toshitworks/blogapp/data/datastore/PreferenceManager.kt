package tech.toshitworks.blogapp.data.datastore

import kotlinx.coroutines.flow.Flow

interface PreferenceManager {
    suspend fun saveToken(token: String)
    fun getToken(): Flow<String?>

    suspend fun saveCategoryViewed()
    fun getCategoryViewed(): Flow<Boolean?>

    suspend fun saveOnboardingViewed()
    fun getOnboardingViewed(): Flow<Boolean?>
}