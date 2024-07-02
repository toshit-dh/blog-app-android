package tech.toshitworks.blogapp.presentation.add_post

import android.content.Context
import android.net.Uri
import tech.toshitworks.blogapp.domain.model.CategoryBody

sealed class AddPostEvents{
    data object OnCloseIconClick: AddPostEvents()
    data object OnSearchIconClick: AddPostEvents()
    data class OnSearchQueryChange(val query: String): AddPostEvents()
    data class OnAddPost(val content: String,val context: Context): AddPostEvents()
    data class OnCategoryAdd(val categoryBody: CategoryBody): AddPostEvents()
    data class OnAddChangePhoto(val image: Uri?): AddPostEvents()
    data object OnChangeEditorControlVisibility: AddPostEvents()
    data object OnChangeImageVisibility: AddPostEvents()
    data object OnRemoveImage: AddPostEvents()
    data object OnRemoveCategory: AddPostEvents()
    data class OnTitleChange(val title: String): AddPostEvents()
    data class OnCommentTitleChange(val title: String): AddPostEvents()
    data class OnCommentBodyChange(val body: String): AddPostEvents()
    data object OnAddCategory: AddPostEvents()
}