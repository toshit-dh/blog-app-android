package tech.toshitworks.blogapp.presentation.home

import tech.toshitworks.blogapp.domain.model.CategoryBody
import tech.toshitworks.blogapp.domain.model.PostBody

data class HomeStates (
    val searchQuery: String = "",
    val categories: List<CategoryBody> = emptyList(),
    val posts: List<PostBody> = emptyList(),
    val isSearchBarVisible: Boolean = false,
    val category: Int = 0,
)