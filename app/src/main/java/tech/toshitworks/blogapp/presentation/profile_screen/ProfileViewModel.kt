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
import tech.toshitworks.blogapp.utils.Resource
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
            when (val user = blogAppRepository.getUserById(id)){
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    _state.update {
                        it.copy(user = user.data!!.toUser())
                    }
                }
            }
            when (val posts = blogAppRepository.getPostsByUserID(id)){
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    _state.update { states ->
                        states.copy(posts = posts.data!!.map { it.toPostBody() })
                }

                }                }
            _isLoading.value = false
        }
    }

}