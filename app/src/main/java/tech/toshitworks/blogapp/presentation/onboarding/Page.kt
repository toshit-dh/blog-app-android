package tech.toshitworks.blogapp.presentation.onboarding

import androidx.annotation.DrawableRes
import tech.toshitworks.blogapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Choose categories",
        description = "Choose your top 20 categories from here",
        image = R.drawable.category
    ),
    Page(
        title = "Home Screen",
        description = "View all the posts of different categories",
        image = R.drawable.home
    ),
    Page(
        title = "Add Post",
        description = "Add a new post",
        image = R.drawable.addpost
    ),
    Page(
        title = "Profile",
        description = "View your profile and posts",
        image = R.drawable.profile
    ),
    Page(
        title = "Post Screen",
        description = "Read a blog from here",
        image = R.drawable.post
    ),
    Page(
        title = "Add Comment",
        description = "Add a comment to a post",
        image = R.drawable.comment
    )
)
