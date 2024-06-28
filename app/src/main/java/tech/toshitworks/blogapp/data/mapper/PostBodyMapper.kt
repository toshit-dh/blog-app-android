package tech.toshitworks.blogapp.data.mapper

import tech.toshitworks.blogapp.data.remote.PostBodyDto
import tech.toshitworks.blogapp.data.remote.PostResponseBodyDto
import tech.toshitworks.blogapp.data.remote.UserDto
import tech.toshitworks.blogapp.domain.model.PostBody
import tech.toshitworks.blogapp.domain.model.PostResponseBody
import tech.toshitworks.blogapp.domain.model.User

fun PostBodyDto.toPostBody() = PostBody(
    content = this.content,
    title = this.title,
    date = this.date,
    id = this.id,
    image = this.image,
    user = User(
        id = this.user.id,
        name = this.user.name,
        about = this.user.about
    )
)

fun PostBody.toPostBodyDto() = PostBodyDto(
    content = this.content,
    title = this.title,
    date = this.date,
    id = this.id,
    image = this.image,
    user = UserDto(
        id = this.user.id,
        name = this.user.name,
        about = this.user.about
    )
)

fun PostResponseBody.toPostResponseDto() =  PostResponseBodyDto(
    content = this.content.map {
        it.toPostBodyDto()
    },
    pageNo = this.pageNo,
    pageSize = this.pageSize,
    totalPages = this.totalPages,
    totalElements = this.totalElements,
    lastPage = this.lastPage
)

fun PostResponseBodyDto.toPostResponseBody() =  PostResponseBody(
    content = this.content.map {
        it.toPostBody()
    },
    pageNo = this.pageNo,
    pageSize = this.pageSize,
    totalPages = this.totalPages,
    totalElements = this.totalElements,
    lastPage = this.lastPage
)