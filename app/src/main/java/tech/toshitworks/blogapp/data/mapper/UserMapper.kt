package tech.toshitworks.blogapp.data.mapper

import tech.toshitworks.blogapp.data.remote.UserDto
import tech.toshitworks.blogapp.domain.model.User

fun UserDto.toUser() = User(
    id = this.id,
    name = this.name,
    about = this.about
)

fun User.toUserDto() = UserDto(
    id = this.id,
    name = this.name,
    about = this.about
)