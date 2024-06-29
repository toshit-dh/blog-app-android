package tech.toshitworks.blogapp.presentation.post

sealed class PostEvents {

    data class OnCommentChange(val comment: String): PostEvents()
    data object OnCommentSend: PostEvents()

}