package com.pwhs.quickmem.data.mapper.pixabay

import com.pwhs.quickmem.data.dto.pixabay.SearchImageResponseDto
import com.pwhs.quickmem.domain.model.pixabay.SearchImageResponseModel


fun SearchImageResponseModel.toDto() = SearchImageResponseDto(
    total = total,
    totalHits = totalHits,
    images = images.map { it.toDto() }
)

fun SearchImageResponseDto.toModel() = SearchImageResponseModel(
    total = total,
    totalHits = totalHits,
    images = images.map { it.toModel() }
)