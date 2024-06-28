package tech.toshitworks.blogapp.presentation.categories

import tech.toshitworks.blogapp.domain.model.CategoryBody

sealed class CategoryEvents {
    data class OnCategoryClick(val categoryBody: CategoryBody,val addOrRemove: Boolean): CategoryEvents()
    data object OnEmptyList: CategoryEvents()
    data object SaveCategory: CategoryEvents()
}