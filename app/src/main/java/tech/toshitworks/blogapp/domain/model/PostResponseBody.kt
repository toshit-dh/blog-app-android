package tech.toshitworks.blogapp.domain.model

data class PostResponseBody(
    val content: List<PostBody>,

    val pageNo: Int,

    val pageSize: Int,

    val totalPages: Int,

    val totalElements: Int,

    val lastPage: Boolean
)