package tech.toshitworks.blogapp.domain.model

data class SignUpBody(
    val about: String,
    val email: String,
    val name: String,
    val password: String
)