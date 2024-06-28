package tech.toshitworks.blogapp.presentation.home

import tech.toshitworks.blogapp.domain.model.CategoryBody

sealed class HomeEvents {
    data class OnSearchQueryChanged(val searchQuery: String) : HomeEvents()
    data object OnSearchIconClicked: HomeEvents()
    data object OnCloseIconClicked: HomeEvents()
    data class OnCategoryChanged(val categoryId: Int): HomeEvents()
}