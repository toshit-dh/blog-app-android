package tech.toshitworks.blogapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.blogapp.data.datastore.PreferenceManager
import tech.toshitworks.blogapp.data.mapper.toCategoryBody
import tech.toshitworks.blogapp.data.mapper.toPostBody
import tech.toshitworks.blogapp.domain.BlogAppRepository
import tech.toshitworks.blogapp.domain.model.PostBody
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val blogAppRepository: BlogAppRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _state = MutableStateFlow(HomeStates())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            val categories = preferenceManager.getCategories().first()
            val categoryBody = categories.map {
                blogAppRepository.getCategoryById(it.toInt()).data!!.toCategoryBody()
            }
            println(categoryBody)
            val post = blogAppRepository.getAllPost().data!!.content.map {
                it.toPostBody()
            }
            _state.update {
                it.copy(categories = categoryBody, posts = post)
            }
            _isLoading.value = false
        }
    }

    private fun getSearchedPost(query: String){
        _state.update {
            it.copy(posts = emptyList())
        }
            viewModelScope.launch {
                _isLoading.value = true
                _state.update {
                    it.copy(posts = blogAppRepository.getSearchedPost(query).data!!.content.map {pbd->
                        pbd.toPostBody()
                    })
                }
            }
        }

//    private fun getCategoryPost(id: Int){
//        _state.update {
//            it.copy(posts = emptyList())
//        }
//        viewModelScope.launch {
//            _isLoading.value = true
//            val posts = if (id == 0){
//                blogAppRepository.getPostByCategory(id).data!!.map{pbd->
//                    pbd.toPostBody()
//                }
//            }else{
//                blogAppRepository.getAllPost().data!!.map {
//                    it.toPostBody()
//                }
//            }
//            _state.update {
//                it.copy(posts = posts)
//            }
//        }
//    }


        fun onEvent(event: HomeEvents) {
            when (event) {
                HomeEvents.OnCloseIconClicked -> {
                    _state.update {
                        it.copy(isSearchBarVisible = false)
                    }
                    //getCategoryPost(state.value.category)
                }

                HomeEvents.OnSearchIconClicked -> {
                    _state.update {
                        it.copy(isSearchBarVisible = true, posts = emptyList())
                    }

                }

                is HomeEvents.OnSearchQueryChanged -> {
                    _state.update {
                        it.copy(searchQuery = event.searchQuery)
                    }
                    searchJob?.cancel()
                    searchJob = viewModelScope.launch {
                        delay(1000)
                        getSearchedPost(event.searchQuery)
                    }
                }

                is HomeEvents.OnCategoryChanged -> {
                    _state.update {
                        it.copy(category = event.categoryId)
                    }
                    //getCategoryPost(state.value.category)
                }
            }
        }
}

