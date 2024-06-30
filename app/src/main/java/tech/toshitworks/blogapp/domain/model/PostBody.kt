package tech.toshitworks.blogapp.domain.model

data class PostBody(
    val content: String,
    val date: String? = null,
    val id: Int? = null,
    val image: String? = null,
    val title: String,
    val user: User
)