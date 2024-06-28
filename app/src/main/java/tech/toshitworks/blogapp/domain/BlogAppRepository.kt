package tech.toshitworks.blogapp.domain

import retrofit2.Response
import tech.toshitworks.blogapp.data.remote.CategoryBodyDto
import tech.toshitworks.blogapp.data.remote.LoginBodyDto
import tech.toshitworks.blogapp.data.remote.PostBodyDto
import tech.toshitworks.blogapp.data.remote.PostResponseBodyDto
import tech.toshitworks.blogapp.data.remote.SignUpBodyDto
import tech.toshitworks.blogapp.data.remote.TokenDto
import tech.toshitworks.blogapp.utils.Resource

interface BlogAppRepository {

    suspend fun signUp(
        signUpBodyDto: SignUpBodyDto
    ): Resource<SignUpBodyDto>

    suspend fun login(
        loginBodyDto: LoginBodyDto
    ): Resource<TokenDto>

    suspend fun verify(): Resource<Boolean>

    suspend fun getAllCategory(): Resource<List<CategoryBodyDto>>

    suspend fun getCategoryById(id: Int): Resource<CategoryBodyDto>

    suspend fun getPostByCategory(id: Int): Resource<List<PostBodyDto>>

    suspend fun getAllPost(): Resource<PostResponseBodyDto>

    suspend fun getSearchedPost(query: String): Resource<PostResponseBodyDto>
}