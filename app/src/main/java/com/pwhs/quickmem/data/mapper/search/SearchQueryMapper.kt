package com.pwhs.quickmem.data.mapper.search

import com.pwhs.quickmem.data.local.entities.SearchQueryEntity
import com.pwhs.quickmem.domain.model.search.SearchQueryModel

fun SearchQueryEntity.toModel() = SearchQueryModel(
    id = id,
    query = query,
    timestamp = timestamp
)

fun SearchQueryModel.toEntity() = SearchQueryEntity(
    id = id,
    query = query,
    timestamp = timestamp
)