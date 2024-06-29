package tech.toshitworks.blogapp.data.mapper

import tech.toshitworks.blogapp.data.remote.CommentBodyDto
import tech.toshitworks.blogapp.domain.model.CommentBody

fun CommentBodyDto.toCommentBody() =  CommentBody(
    date = this.date,
    content = this.content,
    user = this.user
)

fun CommentBody.toCommentBodyDto() =  CommentBodyDto(
    date = this.date,
    content = this.content,
    user = this.user
)