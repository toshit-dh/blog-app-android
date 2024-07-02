package tech.toshitworks.blogapp.data.remote

data class PostResponseBodyDto(
    val content: List<PostBodyDto>,

    val pageNo: Int,

    val pageSize: Int,

    val totalPages: Int,

    val totalElements: Int,

    val lastPage: Boolean
)