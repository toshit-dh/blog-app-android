package tech.toshitworks.blogapp.presentation.sign_up

import tech.toshitworks.blogapp.domain.model.SignUpBody

data class SignUpStates(
    val signUpBody: SignUpBody = SignUpBody("","","",""),
    val error: String? = null,
    val isLoading: Boolean = false,
    val inAbout: Boolean = false
)
