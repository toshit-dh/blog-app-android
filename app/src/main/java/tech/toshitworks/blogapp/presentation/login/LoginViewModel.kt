package tech.toshitworks.blogapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.blogapp.data.datastore.PreferenceManager
import tech.toshitworks.blogapp.data.mapper.toLoginBodyDto
import tech.toshitworks.blogapp.domain.BlogAppRepository
import tech.toshitworks.blogapp.utils.SnackBarEvent
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val blogAppRepository: BlogAppRepository,
    private val preferenceManager: PreferenceManager
): ViewModel(){

    private val _state = MutableStateFlow(LoginStates())
    val state = _state.asStateFlow()

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    fun onEvent(event: LoginEvents){
        when(event){
            is LoginEvents.OnLoginBodyChange -> {
                _state.update {
                    it.copy(loginBody = event.loginBody)
                }
            }
            LoginEvents.OnLogin -> {
                viewModelScope.launch {
                    val token = blogAppRepository.login(_state.value.loginBody.toLoginBodyDto())
                    token.data?.token?.let {
                        preferenceManager.saveToken(it)
                        _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar("Login Successful"))
                    }
                }
            }
        }
    }
}