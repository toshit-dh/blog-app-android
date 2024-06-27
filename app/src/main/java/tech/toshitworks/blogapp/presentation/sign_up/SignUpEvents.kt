package tech.toshitworks.blogapp.presentation.sign_up

import tech.toshitworks.blogapp.domain.model.SignUpBody

sealed class SignUpEvents {
    data class OnSignUpBodyChange(val signUpBody: SignUpBody): SignUpEvents()
    data object OnNextClick: SignUpEvents()
    data object OnBackClick: SignUpEvents()
    data object OnSignUpClick: SignUpEvents()
}