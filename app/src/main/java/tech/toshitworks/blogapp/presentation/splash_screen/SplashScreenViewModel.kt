package tech.toshitworks.blogapp.presentation.splash_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tech.toshitworks.blogapp.data.datastore.PreferenceManager
import tech.toshitworks.blogapp.domain.BlogAppRepository
import tech.toshitworks.blogapp.utils.Routes
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager,
    private val blogAppRepository: BlogAppRepository
): ViewModel() {
    private val _loadingState :MutableState<Boolean> = mutableStateOf(true)
    private val _screenRoute: MutableState<String> = mutableStateOf(Routes.SignUpScreen.route)
    val loadingState: State<Boolean> = _loadingState
    val screenState: State<String> = _screenRoute
    init {
        viewModelScope.launch {
            preferenceManager.getOnboardingViewed().collect {
                if (it == true) {
                    val isVerified = blogAppRepository.verify().data ?: false
                    if (isVerified) {
                        val categoryViewed = preferenceManager.getCategories().first()
                        if (categoryViewed.isNotEmpty() && categoryViewed.size in 5..20) {
                            _screenRoute.value = Routes.HomeScreen.route
                        } else {
                            _screenRoute.value = Routes.CategoryScreen.route
                        }
                    } else {
                        _screenRoute.value = Routes.LoginScreen.route
                    }
                } else {
                    _screenRoute.value = Routes.OnBoardingScreen.route
                }
                _loadingState.value = false
            }
        }
    }
}