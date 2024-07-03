package tech.toshitworks.blogapp.data.datastore

import kotlinx.coroutines.flow.Flow

interface PreferenceManager {
    suspend fun saveToken(token: String)
    suspend fun deleteToken()
    fun getToken(): Flow<String?>


    suspend fun saveOnboardingViewed()
    fun getOnboardingViewed(): Flow<Boolean?>

    suspend fun saveCategories(set: Set<String>)
    fun getCategories(): Flow<Set<String>>
}