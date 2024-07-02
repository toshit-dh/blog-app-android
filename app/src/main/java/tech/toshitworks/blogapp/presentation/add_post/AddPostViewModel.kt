package tech.toshitworks.blogapp.presentation.add_post

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import tech.toshitworks.blogapp.data.mapper.toCategoryBody
import tech.toshitworks.blogapp.data.mapper.toPostBodyDto
import tech.toshitworks.blogapp.data.remote.CategoryBodyDto
import tech.toshitworks.blogapp.domain.BlogAppRepository
import tech.toshitworks.blogapp.domain.model.PostBody
import tech.toshitworks.blogapp.domain.model.User
import tech.toshitworks.blogapp.utils.Resource
import tech.toshitworks.blogapp.utils.SnackBarEvent
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val blogAppRepository: BlogAppRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AddPostStates())
    val state = _state.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    private var searchJob: Job? = null

    private fun updateError(content: String) {
        val errorMessages = mutableListOf<String>()
        if (state.value.title.isEmpty() || state.value.title.length !in 10..100) {
            errorMessages.add(TITLE_SIZE)
        }
        if (state.value.selectedCategory == null) {
            errorMessages.add(CATEGORY_THERE)
        }
        println("hi ${content.length}")
        if (content.isEmpty() || content.length !in 100..10000) {
            errorMessages.add(BODY_SIZE)
        }
        _state.update {
            it.copy(error = errorMessages.toTypedArray())
        }
    }


    private fun getCategoryByTitle(title: String) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val category = blogAppRepository.getCategoryByTitle(title)) {
                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(categories = category.data!!.map { cbd ->
                            cbd.toCategoryBody()
                        })
                    }
                }
            }

            _isLoading.value = false
        }
    }


    fun onEvent(event: AddPostEvents) {
        when (event) {
            is AddPostEvents.OnAddPost -> {
                viewModelScope.launch {
                    updateError(event.content)
                    if (state.value.error.isNotEmpty()) {
                        var error = ""
                        state.value.error.map {
                            error += it
                        }
                        _snackBarEventFlow.emit(
                            SnackBarEvent.ShowSnackBar(
                                message = error
                            )
                        )
                    } else {
                        viewModelScope.launch {
                            val id = state.value.selectedCategory!!.id
                            val content = event.content
                            val postBody = PostBody(
                                title = state.value.title,
                                content = content,
                                user = User("", 0, "")
                            )
                            val contentUri = state.value.image // Assuming this is your content URI
                            val filePath = contentUri?.let { getFilePathFromUri(it, event.context) }

                            val filePart = if (filePath != null) {
                                val file = File(filePath)
                                val fileBody: RequestBody =
                                    file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                                fileBody.let {
                                    MultipartBody.Part.createFormData("image", file.name, it)
                                }
                            } else null
                            val post = blogAppRepository.addPost(
                                filePart,
                                postBody.toPostBodyDto(),
                                id!!
                            )
                            when (post) {
                                is Resource.Error -> {
                                    _snackBarEventFlow.emit(
                                        SnackBarEvent.ShowSnackBar(
                                            message = "Can't add post ${post.message}"
                                        )
                                    )
                                }

                                is Resource.Loading -> {
                                    _snackBarEventFlow.emit(
                                        SnackBarEvent.ShowSnackBar(
                                            message = ""
                                        )
                                    )
                                }

                                is Resource.Success -> {
                                    val addedPost = post.data!!
                                    println(addedPost)
                                    _state.update {
                                        it.copy(
                                            postId = addedPost.id
                                        )
                                    }
                                    _snackBarEventFlow.emit(
                                        SnackBarEvent.ShowPostSnackBar(
                                            id = addedPost.id!!
                                        )
                                    )
                                }
                            }
                        }
                    }


                }
            }

            AddPostEvents.OnCloseIconClick -> {
                _state.update {
                    it.copy(isSearchBarVisible = false)
                }
            }

            AddPostEvents.OnSearchIconClick -> {
                _state.update {
                    it.copy(isSearchBarVisible = true)
                }
            }

            is AddPostEvents.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = event.query)
                }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000)
                    getCategoryByTitle(event.query)
                }
            }

            is AddPostEvents.OnCategoryAdd -> {
                _state.update {
                    it.copy(selectedCategory = event.categoryBody)
                }
            }

            is AddPostEvents.OnAddChangePhoto -> {
                _state.update {
                    it.copy(image = event.image)
                }
            }

            is AddPostEvents.OnChangeEditorControlVisibility -> {
                _state.update {
                    val isVisible = state.value.showEditorControls
                    it.copy(showEditorControls = !isVisible)
                }
            }

            AddPostEvents.OnChangeImageVisibility -> {
                _state.update {
                    val isVisible = state.value.showImage
                    it.copy(showImage = !isVisible)
                }
            }

            AddPostEvents.OnRemoveImage -> {
                _state.update {
                    it.copy(image = null)
                }
            }

            AddPostEvents.OnRemoveCategory -> {
                _state.update {
                    it.copy(selectedCategory = null)
                }
            }

            is AddPostEvents.OnTitleChange -> {
                _state.update {
                    it.copy(title = event.title)
                }
            }

            is AddPostEvents.OnCommentBodyChange -> {
                _state.update {
                    it.copy(
                        commentBody = event.body
                    )
                }
            }

            is AddPostEvents.OnCommentTitleChange -> {
                _state.update {
                    it.copy(
                        commentTitle = event.title
                    )
                }
            }

            AddPostEvents.OnAddCategory -> {
                viewModelScope.launch {
                    val category = blogAppRepository.addCategory(
                        CategoryBodyDto(
                            title = state.value.commentTitle,
                            description = state.value.commentBody
                        )
                    )
                    when(category){
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    selectedCategory = category.data?.toCategoryBody()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getFilePathFromUri(uri: Uri, context: Context): String? {
        val resolver: ContentResolver = context.contentResolver
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = resolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
        return null
    }
}