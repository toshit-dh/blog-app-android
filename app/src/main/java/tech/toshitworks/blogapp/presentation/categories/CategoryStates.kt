package tech.toshitworks.blogapp.presentation.categories

import tech.toshitworks.blogapp.domain.model.CategoryBody

data class CategoryStates(
    val allCategory: List<CategoryBody>? = emptyList(),
    val categorySelected: MutableList<CategoryBody> = mutableListOf<CategoryBody>(),
)