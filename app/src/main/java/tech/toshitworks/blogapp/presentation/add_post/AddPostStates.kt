package tech.toshitworks.blogapp.presentation.add_post

import tech.toshitworks.blogapp.domain.model.CategoryBody
import java.io.File

data class AddPostStates (
    val searchQuery: String = "",
    val content: String = "",
    val image: File? = null,
    val isSearchBarVisible: Boolean = false,
    val categories: List<CategoryBody> = emptyList(),
    val selectedCategory: CategoryBody? = null,
    val error: String? = null
)