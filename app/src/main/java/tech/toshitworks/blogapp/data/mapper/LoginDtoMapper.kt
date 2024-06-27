package tech.toshitworks.blogapp.data.mapper

import tech.toshitworks.blogapp.data.remote.LoginBodyDto
import tech.toshitworks.blogapp.domain.model.LoginBody

fun LoginBodyDto.toLoginBody() = LoginBody(
    email = this.email,
    password = this.password
)

fun LoginBody.toLoginBodyDto() = LoginBodyDto(
    email = this.email,
    password = this.password
)