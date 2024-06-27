package tech.toshitworks.blogapp.domain.model

data class LoginBody(
    val email: String,
    val password: String
)