package tech.toshitworks.blogapp.presentation.profile_screen

import tech.toshitworks.blogapp.domain.model.PostBody
import tech.toshitworks.blogapp.domain.model.User

data class ProfileStates(
    val user: User = User("",0,""),
    val posts: List<PostBody> = emptyList(),
)
