package com.pwhs.quickmem.data.mapper.subject

import com.pwhs.quickmem.data.dto.subject.SubjectResponseDto
import com.pwhs.quickmem.domain.model.subject.SubjectModel

fun SubjectResponseDto.toSubjectModel() = SubjectModel(
    id = id,
    name = name,
)

fun SubjectModel.toSubjectResponseDto() = SubjectResponseDto(
    id = id,
    name = name,
)