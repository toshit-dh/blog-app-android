package tech.toshitworks.blogapp.data.repository

import okio.IOException
import tech.toshitworks.blogapp.data.remote.BlogApi
import tech.toshitworks.blogapp.data.remote.LoginBodyDto
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
}