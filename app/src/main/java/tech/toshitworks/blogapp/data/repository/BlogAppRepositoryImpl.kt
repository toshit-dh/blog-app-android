package tech.toshitworks.blogapp.data.repository

import okio.IOException
import tech.toshitworks.blogapp.data.remote.BlogApi
import tech.toshitworks.blogapp.data.remote.CategoryBodyDto
import tech.toshitworks.blogapp.data.remote.CommentBodyDto
import tech.toshitworks.blogapp.data.remote.LoginBodyDto
import tech.toshitworks.blogapp.data.remote.PostBodyDto
import tech.toshitworks.blogapp.data.remote.PostResponseBodyDto
import tech.toshitworks.blogapp.data.remote.SignUpBodyDto
import tech.toshitworks.blogapp.data.remote.TokenDto
import tech.toshitworks.blogapp.domain.BlogAppRepository
import tech.toshitworks.blogapp.utils.Resource
import java.net.HttpRetryException
import javax.inject.Inject

class BlogAppRepositoryImpl @Inject constructor(
    private val blogApi: BlogApi
): BlogAppRepository {
    override suspend fun signUp(signUpBodyDto: SignUpBodyDto): Resource<SignUpBodyDto> {
        return try {
            val response = blogApi.signUp(signUpBodyDto)
            Resource.Success(response.body())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpRetryException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }

    override suspend fun login(loginBodyDto: LoginBodyDto): Resource<TokenDto> {
        return try {
            val response = blogApi.login(loginBodyDto)
            Resource.Success(response.body())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpRetryException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }

    override suspend fun verify(): Resource<Boolean> {
        return try {
            val response = blogApi.verify()
            Resource.Success(response.body())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpRetryException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }

    override suspend fun getAllCategory(): Resource<List<CategoryBodyDto>> {
        return try {
            val response = blogApi.getAllCategory()
            Resource.Success(response.body())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpRetryException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }

    override suspend fun getCategoryById(id: Int): Resource<CategoryBodyDto> {
        return try {
            val response = blogApi.getCategoryById(id)
            Resource.Success(response.body())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpRetryException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }

    override suspend fun getCategoryByTitle(title: String): Resource<List<CategoryBodyDto>> {
        return try {
            val response = blogApi.getCategoryByTitle(title)
            Resource.Success(response.body())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpRetryException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }

    override suspend fun getPostByCategory(id: Int): Resource<List<PostBodyDto>> {
        return try {
            val response = blogApi.getPostByCategory(id)
            Resource.Success(response.body())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpRetryException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }

    override suspend fun getAllPost(): Resource<PostResponseBodyDto> {
        return try {
            val response = blogApi.getAllPost()
            Resource.Success(response.body())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpRetryException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }

    override suspend fun getPostById(id: Int): Resource<PostBodyDto> {
        return try {
            val response = blogApi.getPostById(id)
            Resource.Success(response.body())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpRetryException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }

    override suspend fun getSearchedPost(query: String): Resource<PostResponseBodyDto> {
        return try {
            val response = blogApi.getPostByQuery(query)
            Resource.Success(response.body())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpRetryException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }

    override suspend fun getCommentPyPost(id: Int): Resource<List<CommentBodyDto>> {
        return try {
            val response = blogApi.getCommentsByPost(id)
            Resource.Success(response.body())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpRetryException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }
}