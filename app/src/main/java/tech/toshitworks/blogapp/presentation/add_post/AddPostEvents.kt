package tech.toshitworks.blogapp.presentation.add_post

import tech.toshitworks.blogapp.domain.model.CategoryBody

sealed class AddPostEvents{
    data object OnCloseIconClick: AddPostEvents()
    data object OnSearchIconClick: AddPostEvents()
    data class OnContentChange(val content: String): AddPostEvents()
    data class OnSearchQueryChange(val query: String): AddPostEvents()
    data object OnAddPost: AddPostEvents()
    data class OnCategoryAdd(val categoryBody: CategoryBody): AddPostEvents()
}