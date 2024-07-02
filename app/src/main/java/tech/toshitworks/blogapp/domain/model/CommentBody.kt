package tech.toshitworks.blogapp.domain.model

data class CommentBody(
    val date: String? = null,
    val content: String,
    val user: User
)
