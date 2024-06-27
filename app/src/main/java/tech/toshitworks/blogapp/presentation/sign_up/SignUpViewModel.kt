package tech.toshitworks.blogapp.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.blogapp.data.mapper.toSignUpDto
import tech.toshitworks.blogapp.domain.BlogAppRepository
import tech.toshitworks.blogapp.utils.Resource
import tech.toshitworks.blogapp.utils.SnackBarEvent
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val blogAppRepository: BlogAppRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpStates())
    val state = _state.asStateFlow()

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    fun onEvent(event: SignUpEvents) {
        when (event) {
            SignUpEvents.OnNextClick -> {
                _state.update {
                    it.copy(inAbout = true)
                }
            }

            SignUpEvents.OnBackClick -> {
                _state.update {
                    it.copy(inAbout = false)
                }
            }

            is SignUpEvents.OnSignUpBodyChange -> {
                _state.update {
                    it.copy(signUpBody = event.signUpBody)
                }
            }

            SignUpEvents.OnSignUpClick -> {
                viewModelScope.launch {
                    val signUp = blogAppRepository.signUp(_state.value.signUpBody.toSignUpDto())
                    when(signUp){
                        is Resource.Error -> {
                            _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar("Sign Up unsuccessful.${signUp.message}"))
                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar("Signup successful. Login with same credentials"));
                        }
                    }

                }
            }
        }
    }

}