package tech.toshitworks.blogapp.presentation.post

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.blogapp.data.mapper.toCommentBody
import tech.toshitworks.blogapp.data.mapper.toPostBody
import tech.toshitworks.blogapp.domain.BlogAppRepository
import tech.toshitworks.blogapp.presentation.home.HomeStates
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val blogAppRepository: BlogAppRepository
) : ViewModel(){
    val id = savedStateHandle.get<String>("id")!!.toInt()
    private val _state = MutableStateFlow(PostStates())
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            val post = blogAppRepository.getPostById(id).data!!.toPostBody()
            val comment = blogAppRepository.getCommentPyPost(id).data!!.map {
                it.toCommentBody()
            }
            _state.update {
                it.copy(postBody = post, comments = comment)
            }
            _isLoading.value = false
        }
    }

    fun onEvent(event: PostEvents){
        when(event){
            is PostEvents.OnCommentChange -> {
                _state.update {
                    it.copy(comment = event.comment)
                }
            }

            PostEvents.OnCommentSend -> {

            }
        }
    }
}