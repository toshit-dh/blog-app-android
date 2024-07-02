package tech.toshitworks.blogapp.data.remote

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import tech.toshitworks.blogapp.utils.Constants


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

    @GET(Constants.POST_BY_ID)
    suspend fun getPostById(
        @Path("id") id: Int
    ): Response<PostBodyDto>

    @GET(Constants.COMMENTS_BY_POST)
    suspend fun getCommentsByPost(
        @Path("id") id: Int
    ): Response<List<CommentBodyDto>>

    @GET(Constants.CATEGORY_BY_TITLE)
    suspend fun getCategoryByTitle(
        @Path("title") title: String
    ): Response<List<CategoryBodyDto>>

    @Multipart
    @POST(Constants.ADD_POST)
    suspend fun addPost(
        @Part file: MultipartBody.Part?,
        @Part("post") postBodyDto: PostBodyDto,
        @Path("id") id: Int,
    ): Response<PostBodyDto>

    @POST(Constants.ADD_COMMENT)
    suspend fun addComment(
        @Path("id") id: Int,
        @Body commentBodyDto: CommentBodyDto
    ): Response<CommentBodyDto>

    @GET(Constants.USER_BY_ID)
    suspend fun getUser(
        @Path("id") id: Int
    ): Response<UserDto>

    @POST(Constants.CATEGORY_ADD)
    suspend fun addCategory(
        @Body categoryBodyDto: CategoryBodyDto
    ): Response<CategoryBodyDto>

    @GET(Constants.POST_BY_USER)
    suspend fun getPostByUser(
        @Path("id") id: Int
    ): Response<List<PostBodyDto>>


}