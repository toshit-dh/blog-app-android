package tech.toshitworks.blogapp.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.toshitworks.blogapp.data.datastore.PreferenceManager
import javax.inject.Inject

sealed class OnBoardingEvents{
    data object OnNextButton: OnBoardingEvents()
}

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
): ViewModel() {
    fun onEvent(event: OnBoardingEvents){
        when(event){
            OnBoardingEvents.OnNextButton -> {
                viewModelScope.launch {
                    preferenceManager.saveOnboardingViewed()
                }
            }
        }

    }
}