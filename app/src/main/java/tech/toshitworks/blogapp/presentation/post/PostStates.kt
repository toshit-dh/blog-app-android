package tech.toshitworks.blogapp.presentation.post

import tech.toshitworks.blogapp.domain.model.CommentBody
import tech.toshitworks.blogapp.domain.model.PostBody
import tech.toshitworks.blogapp.domain.model.User

data class PostStates(
    val postBody: PostBody = PostBody("","",0,"","", User("",0,"")),
    val comments: List<CommentBody> = emptyList(),
    val comment: String = ""
)