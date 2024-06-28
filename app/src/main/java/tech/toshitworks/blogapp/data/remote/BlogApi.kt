package tech.toshitworks.blogapp.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import tech.toshitworks.blogapp.utils.Constants
import tech.toshitworks.blogapp.utils.Resource

interface BlogApi {

    @POST(Constants.SIGNUP)
    suspend fun signUp(
        @Body signUpBodyDto: SignUpBodyDto
    ): Response<SignUpBodyDto>

    @POST(Constants.LOGIN)
    suspend fun login(
        @Body loginBodyDto: LoginBodyDto
    ): Response<TokenDto>

    @GET(Constants.VERIFY)
    suspend fun verify(): Response<Boolean>

    @GET(Constants.CATEGORY_ALL)
    suspend fun getAllCategory(): Response<List<CategoryBodyDto>>

    @GET(Constants.CATEGORY_BY_ID)
    suspend fun getCategoryById(
        @Path("id") id: Int
    ): Response<CategoryBodyDto>

    @GET(Constants.POST_BY_CATEGORY)
    suspend fun getPostByCategory(
        @Path("id") id: Int
    ): Response<List<PostBodyDto>>

    @GET(Constants.POST_ALL)
    suspend fun getAllPost(): Response<PostResponseBodyDto>

    @GET(Constants.POST_BY_QUERY)
    suspend fun getPostByQuery(
        @Path("keyword") query: String
    ): Response<PostResponseBodyDto>


}