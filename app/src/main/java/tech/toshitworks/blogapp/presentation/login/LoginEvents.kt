package tech.toshitworks.blogapp.presentation.login

import tech.toshitworks.blogapp.domain.model.LoginBody

sealed class LoginEvents {
    data class OnLoginBodyChange(val loginBody: LoginBody): LoginEvents()
    data object OnLogin: LoginEvents()
}