package tech.toshitworks.blogapp.data.mapper

import tech.toshitworks.blogapp.data.remote.SignUpBodyDto
import tech.toshitworks.blogapp.domain.model.SignUpBody

fun SignUpBody.toSignUpDto() = SignUpBodyDto(
    about = this.about,
    email = this.email,
    password = this.password,
    name = this.name
)

fun SignUpBodyDto.toSignUp() = SignUpBody(
    about = this.about,
    email = this.email,
    password = this.password,
    name = this.name
)