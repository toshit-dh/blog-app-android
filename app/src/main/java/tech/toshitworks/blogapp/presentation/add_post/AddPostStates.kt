package tech.toshitworks.blogapp.presentation.add_post

import android.net.Uri
import tech.toshitworks.blogapp.domain.model.CategoryBody

data class AddPostStates (
    val searchQuery: String = "",
    val content: String = "",
    val title: String = "",
    val image: Uri? = null,
    val isSearchBarVisible: Boolean = false,
    val categories: List<CategoryBody> = emptyList(),
    val selectedCategory: CategoryBody? = null,
    val error: Array<String> = arrayOf(TITLE_SIZE, CATEGORY_THERE, BODY_SIZE),
    val showEditorControls: Boolean = true,
    val showImage: Boolean = true,
    val postId: Int? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AddPostStates

        if (searchQuery != other.searchQuery) return false
        if (content != other.content) return false
        if (title != other.title) return false
        if (image != other.image) return false
        if (isSearchBarVisible != other.isSearchBarVisible) return false
        if (categories != other.categories) return false
        if (selectedCategory != other.selectedCategory) return false
        if (!error.contentEquals(other.error)) return false
        if (showEditorControls != other.showEditorControls) return false
        if (showImage != other.showImage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = searchQuery.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + isSearchBarVisible.hashCode()
        result = 31 * result + categories.hashCode()
        result = 31 * result + (selectedCategory?.hashCode() ?: 0)
        result = 31 * result + error.contentHashCode()
        result = 31 * result + showEditorControls.hashCode()
        result = 31 * result + showImage.hashCode()
        return result
    }
}

const val TITLE_SIZE = "Title cannot be empty and must be between 10 and 100 characters.\n"
const val CATEGORY_THERE = "A category must be selected for post.\n"
const val BODY_SIZE = "Post body cannot be empty and must be between 100 and 10000 characters.\n"
const val POST_ADDED = "Post successfully added"