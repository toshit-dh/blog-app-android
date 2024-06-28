package tech.toshitworks.blogapp.data.mapper

import tech.toshitworks.blogapp.data.remote.CategoryBodyDto
import tech.toshitworks.blogapp.domain.model.CategoryBody

fun CategoryBody.toCategoryBodyDto() = CategoryBodyDto(
    id = this.id,
    title = this.title,
    description = this.description
)

fun CategoryBodyDto.toCategoryBody() = CategoryBody(
    id = this.id,
    title = this.title,
    description = this.description
)