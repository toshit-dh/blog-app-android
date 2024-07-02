package tech.toshitworks.blogapp.presentation.profile_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.blogapp.data.mapper.toPostBody
import tech.toshitworks.blogapp.data.mapper.toUser
import tech.toshitworks.blogapp.domain.BlogAppRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val blogAppRepository: BlogAppRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(){

    val id = savedStateHandle.get<String>("id")!!.toInt()

    private val _state = MutableStateFlow(ProfileStates())
    val state = _state.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            val user = blogAppRepository.getUserById(id).data!!.toUser()
            val posts = blogAppRepository.getPostsByUserID(id).data!!.map {
                it.toPostBody()
            }
            _state.update {
                it.copy(user = user, posts = posts)
            }
            _isLoading.value = false
        }
    }

}