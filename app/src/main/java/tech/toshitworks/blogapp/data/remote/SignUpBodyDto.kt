package tech.toshitworks.blogapp.data.remote

data class SignUpBodyDto(
    val about: String,
    val email: String,
    val name: String,
    val password: String
)
