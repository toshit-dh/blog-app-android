package tech.toshitworks.blogapp.data.datastore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
object PreferencesKey {
    val TOKEN= stringPreferencesKey("token")
    val CATEGORY_VIEWED = booleanPreferencesKey("category_viewed")
    val ONBOARDING_VIEWED = booleanPreferencesKey("onboarding_viewed")
}