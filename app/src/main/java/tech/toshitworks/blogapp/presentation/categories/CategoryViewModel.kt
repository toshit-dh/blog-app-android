package tech.toshitworks.blogapp.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.toshitworks.blogapp.data.datastore.PreferenceManager
import tech.toshitworks.blogapp.data.mapper.toCategoryBody
import tech.toshitworks.blogapp.domain.BlogAppRepository
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val blogAppRepository: BlogAppRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel(){

    private val _state = MutableStateFlow(CategoryStates())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it ->
                it.copy(allCategory = blogAppRepository.getAllCategory().data?.map { cbd->
                    cbd.toCategoryBody()
                })
            }
        }
    }

    fun onEvent(event: CategoryEvents){
        when(event){
            is CategoryEvents.OnCategoryClick -> {
                _state.update {state->
                    val newList = if(event.addOrRemove){
                        state.categorySelected + event.categoryBody
                    }else{
                        state.categorySelected - event.categoryBody
                    }
                    state.copy(categorySelected = newList.toMutableList())
                }
            }
            CategoryEvents.OnEmptyList -> {
                _state.update {
                    it.copy(categorySelected = mutableListOf())
                }
            }

            CategoryEvents.SaveCategory -> {
                val setId = state.value.categorySelected.map {
                    it.id.toString()
                }.toSet()
                println(setId)
                viewModelScope.launch {
                    preferenceManager.saveCategories(setId)
                }
            }
        }
    }

}