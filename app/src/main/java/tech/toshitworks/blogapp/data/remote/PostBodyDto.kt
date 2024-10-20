package tech.toshitworks.blogapp.data.remote

data class PostBodyDto(
    val content: String,
    val date: String?,
    val id: Int?,
    val image: String? = null,
    val title: String,
    val user: UserDto
)