package tech.toshitworks.blogapp.presentation.login

import tech.toshitworks.blogapp.domain.model.LoginBody

data class LoginStates(
    val loginBody: LoginBody = LoginBody("","")
)
