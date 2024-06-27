package tech.toshitworks.blogapp.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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


}