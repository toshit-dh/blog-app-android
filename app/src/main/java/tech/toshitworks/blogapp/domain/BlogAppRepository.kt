package tech.toshitworks.blogapp.domain

import okhttp3.MultipartBody
import retrofit2.Response
import tech.toshitworks.blogapp.data.remote.CategoryBodyDto
import tech.toshitworks.blogapp.data.remote.CommentBodyDto
import tech.toshitworks.blogapp.data.remote.LoginBodyDto
import tech.toshitworks.blogapp.data.remote.PostBodyDto
import tech.toshitworks.blogapp.data.remote.PostResponseBodyDto
import tech.toshitworks.blogapp.data.remote.SignUpBodyDto
import tech.toshitworks.blogapp.data.remote.TokenDto
import tech.toshitworks.blogapp.data.remote.UserDto
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

    suspend fun getPostById(id: Int): Resource<PostBodyDto>

    suspend fun getSearchedPost(query: String): Resource<PostResponseBodyDto>

    suspend fun getCommentPyPost(id: Int): Resource<List<CommentBodyDto>>

    suspend fun getCategoryByTitle(title: String): Resource<List<CategoryBodyDto>>

    suspend fun addPost(file: MultipartBody.Part?,postBodyDto: PostBodyDto,id: Int): Resource<PostBodyDto>

    suspend fun addComment(id: Int,commentBodyDto: CommentBodyDto): Resource<CommentBodyDto>

    suspend fun getUserById(id: Int): Resource<UserDto>

    suspend fun addCategory(categoryBodyDto: CategoryBodyDto): Resource<CategoryBodyDto>

    suspend fun getPostsByUserID(id: Int): Resource<List<PostBodyDto>>
}