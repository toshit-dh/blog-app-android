package tech.toshitworks.blogapp.domain

import retrofit2.Response
import tech.toshitworks.blogapp.data.remote.LoginBodyDto
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


}