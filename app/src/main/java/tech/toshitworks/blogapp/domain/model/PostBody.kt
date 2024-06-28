package tech.toshitworks.blogapp.domain.model

data class PostBody(
    val content: String,
    val date: String,
    val id: Int,
    val image: String,
    val title: String,
    val user: User
)