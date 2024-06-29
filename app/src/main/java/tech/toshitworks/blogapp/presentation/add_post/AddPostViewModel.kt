package tech.toshitworks.blogapp.presentation.add_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.blogapp.data.mapper.toCategoryBody
import tech.toshitworks.blogapp.domain.BlogAppRepository
import tech.toshitworks.blogapp.utils.Resource
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val blogAppRepository: BlogAppRepository
): ViewModel(){

    private val _state = MutableStateFlow(AddPostStates())
    val state = _state.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var searchJob: Job? = null

    private fun getCategoryByTitle(title: String){
        _isLoading.value = true
        viewModelScope.launch {
            when(val category = blogAppRepository.getCategoryByTitle(title)){
                is Resource.Error -> {
                    _state.update {
                        it.copy(error = category.message)
                    }
                }
                is Resource.Loading -> {
                    _state.update {
                        it.copy(error = category.message)
                    }
                }
                is Resource.Success -> {
                    _state.update {
                        it.copy(categories = category.data!!.map { cbd->
                            cbd.toCategoryBody()
                        })
                    }
                }
            }

            _isLoading.value = false
        }
    }

    fun onEvent(event: AddPostEvents){
        when(event){
            AddPostEvents.OnAddPost -> {

            }
            AddPostEvents.OnCloseIconClick -> {
                _state.update {
                    it.copy(isSearchBarVisible = false)
                }
            }
            is AddPostEvents.OnContentChange -> {
                _state.update {
                    it.copy(content = event.content)
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
        }
    }

}