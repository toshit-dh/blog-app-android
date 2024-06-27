package tech.toshitworks.blogapp.presentation.categories

import androidx.lifecycle.ViewModel
import tech.toshitworks.blogapp.domain.BlogAppRepository
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    blogAppRepository: BlogAppRepository
) : ViewModel(){
}