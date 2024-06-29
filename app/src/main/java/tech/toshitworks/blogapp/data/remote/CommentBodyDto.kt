package tech.toshitworks.blogapp.data.remote

import tech.toshitworks.blogapp.domain.model.User

data class CommentBodyDto(
    val date: String,
    val content: String,
    val user: User
)

